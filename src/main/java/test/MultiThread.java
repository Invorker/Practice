package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MultiThread {
	private int threads;

	private List<FutureTask<Long>> tasks = new ArrayList<FutureTask<Long>>();

	public MultiThread(int threads) {
		this.threads = threads;
	}

	public long calculate(int start, int end) throws InterruptedException, ExecutionException {
		int interval = (end - start) / threads;
		interval = interval == 0 ? 1 : interval;
		int next = start + interval;
		do {
			Call c;
			if (next >= end) {
				c = new Call(start, end);

			} else {
				c = new Call(start, next);
			}
			start = next + 1;
			next = start + interval;
			FutureTask<Long> ft = new FutureTask<Long>(c);
			tasks.add(ft);
			new Thread(ft, "º∆À„œﬂ≥Ã").start();
		} while (start <= end);
		return total();
	}

	private Long total() throws InterruptedException, ExecutionException {
		Long result = 0L;
		while (!tasks.isEmpty()) {
			Iterator<FutureTask<Long>> iter = tasks.iterator();
			while (iter.hasNext()) {
				FutureTask<Long> ft = iter.next();
				if (ft.isDone()) {
					result += ft.get();
					iter.remove();
				}
			}
		}
		return result;
	}

	class Call implements Callable<Long> {
		private int start;
		private int end;

		public Call(int start, int end) {
			this.start = start;
			this.end = end;
		}

		public Long call() {
			long total = start;
			while (start++ < end)
				total += start;
			return total;
		}
	}

	public static void main(String[] args) {
		long t1, t2;
		t1 = System.currentTimeMillis();
		MultiThread mt = new MultiThread(200);
		try {
			System.out.println(mt.calculate(0, 914748364));

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		t2 = System.currentTimeMillis();
		System.out.println("Run Time:" + (t2 - t1) + "(ms)");
		System.out.println(214748364L*(214748364+1)/2);
		System.out.println(Integer.MAX_VALUE);
	}

}
