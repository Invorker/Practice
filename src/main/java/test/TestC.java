package test;

import java.util.concurrent.*;

public class TestC {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        long start = System.currentTimeMillis();

        new TestC().thenApply();
        System.out.println(System.currentTimeMillis() - start);
    }

    public void run() {
        CompletableFuture<String> stage = CompletableFuture.supplyAsync(() -> "hello");
        Void run = stage.thenRunAsync(() -> System.out.println("run")).join();
        System.out.println(run);
    }

    public void thenApply() {
        CompletableFuture<String> stage = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        });

        String result = stage.thenApplyAsync(s -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s + " world";
        }).join();
        System.out.println(result);
    }

    public void thenCombine() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCombineAsync(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("ex");
        }), (s1, s2) -> s1 + " " + s2, new Ex()).thenCombineAsync(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> s1 + " " + s2, new Ex()).join();
        System.out.println(result);
    }

    public void applyToEither() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Tom";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "John";
        }), s -> "hello " + s).join();
        System.out.println(result);
    }

    public void thenCompose() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCompose(s -> CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s + " world";
        })).join();

        System.out.println(result);
    }

    public void whenComplete() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }

            return "hello ";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("return world...");  //会执行
            return "world";
        }), (s1, s2) -> {
            String s = s1 + " " + s2;   //并不会执行
            System.out.println("combine result :" + s); //并不会执行
            return s;
        }).whenComplete((s, t) -> {
            System.out.println("current result is :" + s);
            if (t != null) {
                System.out.println("阶段执行过程中存在异常：");
//                t.printStackTrace();
            }
        }).join();

        System.out.println("final result:" + result); //并不会执行
    }

    public void handle() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //出现异常
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "Tom";
        }).handle((s, t) -> {
            if (t != null) { //出现异常了
                return "John";
            }
            return s; //这里也可以对正常结果进行转换
        }).join();
        System.out.println(result);
    }

    public void exceptionally() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 3) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).exceptionally(e -> {
            e.printStackTrace(); //e肯定不会null
            return "hello world"; //补偿返回
        }).join();
        System.out.println(result); //打印hello world
    }

    public void test1() throws ExecutionException, InterruptedException {
        //1. 提交一个一部执行的任务,无结果返回值
        CompletableFuture.runAsync(() -> {
            System.out.println("I have done Nothing");
        });

        CompletableFuture.runAsync(() -> {
            System.out.println("Me, too");
        }, new Ex());

        //2. 提交一个一部执行的任务,有结果返回值
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "future 1");
        //若future执行完毕,则future.get();返回执行结果'future 1',若未执行完毕,则返回给定值'111'
        future.complete("111");
        System.out.println(future.get());

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "future 1",
                new Ex());

        //返回一个指定结果的CompletableFuture对象
        CompletableFuture<String> future2 = CompletableFuture.completedFuture("future 2");
    }

    public void test2() throws ExecutionException, InterruptedException, TimeoutException {
        //1. 提交一个一部执行的任务,无结果返回值
        CompletableFuture<Void> void1 = CompletableFuture.runAsync(() -> {
            System.out.println("I have done Nothing");
        });

        CompletableFuture.runAsync(() -> {
            System.out.println("I have done Nothing2");
        }).join();
        System.out.println("first sout");
        CompletableFuture<Void> void2 = CompletableFuture.runAsync(() -> {
            System.out.println("Me, too");
        }, new Ex());

        CompletableFuture.allOf(void1, void2).thenRun(() -> {
            System.out.println("success");
        }).join();


        //2. 提交一个一部执行的任务,有结果返回值
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "future 1");

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "future 3",
                new Ex());

        //后面需要join(),否则void1和void2将异步执行,这里不会阻塞,也就拿不到执行结果
        CompletableFuture.allOf(void1, void2).thenRun(() -> {
            System.out.println("success1");
        }).join();

        CompletableFuture.anyOf(future, future1).thenAccept((value) -> System.out.println(value)); //输出结果future 1与future 3不定

        System.out.println("------------");
        /**
         * 在这里重点对比一下下面的方法,下面举例用allOf,实际allOf与anyOf用法相同,下面提到的join(),换成get()也同样;
         */
        //1.allOf没有join()配合使用,allOf后面的结果集若没有执行完毕则直接执行log.info();结果集继续异步执行,完成后当前异步线程继续执行thenAccept()方法;
        CompletableFuture.allOf(future, future1).thenAccept((value) -> {
            System.out.println(value);
        });
        //2.allOf配合join()使用,线程将会阻塞等待结果集执行完毕获取结果后执行thenAccept();
        CompletableFuture.allOf(future, future1).thenAccept((value) -> {
            System.out.println(value);
        }).join();

        CompletableFuture.allOf(future, future1).thenAccept((value) -> {
            System.out.println(value);
        }).get();

        CompletableFuture.allOf(future, future1).thenAccept((value) -> {
            System.out.println(value);
        }).get(2000, TimeUnit.MILLISECONDS);

        System.out.println("do nothing");

    }

    public static class Ex implements Executor {

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }
}
