package io.github.phantomstr.testing.tool.utils.wait;

import io.github.phantomstr.testing.tool.utils.time.TimeUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Slf4j
public class WaitTaskUtils {
    /**
     * execute callable function awaited not null value (otherwise will be timeout exception)
     *
     * @param callable    function to execute
     * @param msecTimeout timeout of execution
     * @param onTimeout   message on timeout
     * @param <O>         return object type
     * @return result of callable function
     * @throws Throwable callable method exception, InterruptedException, TimeoutException
     */
    public static <O> O waitTask(Callable<O> callable,
                                 long msecTimeout,
                                 String onTimeout) throws Throwable {
        O result = null;

        FutureTask<O> futureTask = new FutureTask<>(callable);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            executor.submit(futureTask);
            result = futureTask.get(msecTimeout, MILLISECONDS);
        } catch (InterruptedException ignore) {
        } catch (ExecutionException e) {
            throw e.getCause() != null ? e.getCause() : e;
        }


        if (result == null) {
            futureTask.cancel(true);
            executor.shutdown();
            throw new TimeoutException(String.format("%s (Timeout %s)", onTimeout, TimeUtils.humanReadableFormat(Duration.of(msecTimeout, MILLIS))));
        }
        return result;
    }

    /**
     * execute periodical task and wait complete
     *
     * @param callable    function to execute
     * @param msecPeriod  recall period
     * @param msecTimeout global timeout msec.
     * @param onTimeout   message on timeout
     * @param <O>         return object type
     * @return result of callable function
     * @throws Throwable callable method exception, InterruptedException, TimeoutException
     */
    public static <O> O waitTask(Callable<O> callable,
                                 long msecPeriod,
                                 long msecTimeout,
                                 String onTimeout) throws Throwable {
        // concurrent queues for result/exception. First value will be returned.
        LinkedTransferQueue<O> queue = new LinkedTransferQueue<>();
        LinkedTransferQueue<Exception> ex = new LinkedTransferQueue<>();

        // periodical run task and fill result if possible
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        producer(callable, msecPeriod, queue, ex, service);

        // wait any result
        try {
            return consumer(msecTimeout, onTimeout, queue, ex);
        } finally {
            // interrupt all tasks
            service.shutdown();
        }
    }

    private static <O> void producer(Callable<O> callable, long msecPeriod, LinkedTransferQueue<O> queue, LinkedTransferQueue<Exception> ex, ScheduledExecutorService service) {
        service.scheduleAtFixedRate(() -> {
            try {
                log.debug("run task");
                O call = callable.call();
                if (queue.isEmpty() && call != null) {
                    log.info("task done with result {}", call);
                    queue.offer(call);
                }
            } catch (InterruptedException ignored) {
                //task can be interrupted if result already exist
            } catch (Exception e) {
                log.debug("task done with exception", e);
                ex.offer(e);
            }
            log.debug("end task. success={}", !queue.isEmpty());
        }, 0, msecPeriod, MILLISECONDS);
    }

    private static <O> O consumer(long msecTimeout, String onTimeout, LinkedTransferQueue<O> queue, LinkedTransferQueue<Exception> ex) throws Throwable {
        return waitTask(
                () -> {
                    log.debug("wait task done");
                    while (ex.isEmpty() && queue.isEmpty()) {
                        // unlock queues for writing
                        sleep(7);
                    }
                    if (!queue.isEmpty()) {
                        O take = queue.peek();
                        log.debug("wait task result: {}", take);
                        return take;
                    }
                    Exception exception = ex.peek();
                    log.debug("wait task result: exception", exception);
                    throw exception;
                }, msecTimeout, onTimeout);
    }

}
