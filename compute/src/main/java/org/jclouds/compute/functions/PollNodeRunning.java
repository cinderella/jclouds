package org.jclouds.compute.functions;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static org.jclouds.compute.config.ComputeServiceProperties.TIMEOUT_NODE_RUNNING;
import static org.jclouds.compute.util.ComputeServiceUtils.formatStatus;

import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;
import javax.inject.Named;

import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.NodeMetadata.Status;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Stopwatch;

/**
 * Polls until the node is running or throws {@link IllegalStateException} if
 * this cannot be achieved within the timeout.
 *
 * @author Adrian Cole
 *
 */
@Named(TIMEOUT_NODE_RUNNING)
public class PollNodeRunning implements Function<AtomicReference<NodeMetadata>, AtomicReference<NodeMetadata>> {
   private final Predicate<AtomicReference<NodeMetadata>> nodeRunning;

   @Inject
   public PollNodeRunning(@Named(TIMEOUT_NODE_RUNNING) Predicate<AtomicReference<NodeMetadata>> nodeRunning) {
      this.nodeRunning = checkNotNull(nodeRunning, "nodeRunning");
   }

   /**
    * @param node
    *           will be updated with the node which is running
    * @throws {@link IllegalStateException} if this cannot be achieved within
    *         the timeout.
    */
   @Override
   public AtomicReference<NodeMetadata> apply(AtomicReference<NodeMetadata> node) throws IllegalStateException {
      String originalId = node.get().getId();
      NodeMetadata originalNode = node.get();
      try {
         Stopwatch stopwatch = new Stopwatch().start();
         if (!nodeRunning.apply(node)) {
            long timeWaited = stopwatch.elapsedMillis();
            if (node.get() == null) {
               node.set(originalNode);
               throw new IllegalStateException(format("api response for node(%s) was null", originalId));
            } else {
               throw new IllegalStateException(format(
                     "node(%s) didn't achieve the status running; aborting after %d seconds with final status: %s",
                     originalId, timeWaited / 1000, formatStatus(node.get())));
            }
         }
      } catch (IllegalStateException e) {
         if (node.get().getStatus() == Status.TERMINATED) {
            throw new IllegalStateException(format("node(%s) terminated", originalId));
         } else {
            throw e;
         }
      }
      return node;
   }
}