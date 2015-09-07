import java.util.Arrays;
import java.util.Comparator;

/**
 * Array-backed implementation of a <a
 * href="http://en.wikipedia.org/wiki/Binary_heap">binary heap</a> data
 * structure, with support for {@link #heapify()}, {@link #remove()} and
 * {@link #add(Object)}
 * 
 * @author Chuck Lan
 * 
 * @param <T> Type of objects managed by the heap
 */
public class MyHeap<T> {
	T[] values;
	int size, numOps;
	Comparator<T> comp;

	/**
	 * Constructor
	 * 
	 * @param vals
	 *            an array on values of type <code>T</code>
	 * @param c
	 *            comparator to define ordering
	 */
	public MyHeap(T[] vals, Comparator<T> c) {
		values = Arrays.copyOf(vals, vals.length);
		this.size = vals.length;
		comp = c;
	}

	/**
	 * Returns the index of the child
	 * 
	 * @param idx
	 * @param offset
	 *            1 = left, 2 = right
	 * @return
	 */
	protected int getChildIdx(int idx, int offset /* 1 = left, 2 = right */) {
		return (2 * idx) + offset;
	}

	/**
	 * Return the parent of the current index
	 * 
	 * @param idx
	 * @return
	 */
	protected int getParentIdx(int idx) {
		int retVal = -1;
		if (idx > 0) {
			retVal = (idx % 2 == 0) ? (idx - 2) / 2 : (idx - 1) / 2;
		}
		return retVal;
	}

	/**
	 * Build the heap from the bottom up.
	 */
	public void heapify() {
		for (int idx = size - 1; idx >= 0; idx--) {
			heapDown(idx);
		}
	}

	protected void heapDown(int idx) {
		int leftChildIdx = getChildIdx(idx, 1);
		int rightChildIdx = getChildIdx(idx, 2);
		/* no children */
		if (leftChildIdx >= size) {
			return;
		} else if (rightChildIdx >= size) {
			if (comp.compare(values[leftChildIdx], values[idx]) < 0) {
				swap(idx, leftChildIdx);
				heapDown(leftChildIdx);
			}
		} else {
			int childIndex = (comp.compare(values[leftChildIdx],
					values[rightChildIdx]) < 0) ? leftChildIdx : rightChildIdx;
			if (comp.compare(values[childIndex], values[idx]) < 0) {
				swap(idx, childIndex);
				heapDown(childIndex);
			}
		}
	}

	/**
	 * To support insertions
	 * 
	 * @param idx
	 */
	protected void heapUp(int idx) {
		int parentIdx = getParentIdx(idx);
		if (parentIdx == -1) {
			return;
		} else if (comp.compare(values[idx], values[parentIdx]) < 0) {
			swap(idx, parentIdx);
			heapUp(parentIdx);
		}
	}

	protected void swap(int parentIdx, int idx) {
		T temp = values[parentIdx];
		values[parentIdx] = values[idx];
		values[idx] = temp;
		numOps++;
	}

	/**
	 * Remove the top of the heap, followed by a heap down operation.
	 * 
	 * @return
	 */
	public T remove() {
		T val = values[0];
		swap(0, --size);
		heapDown(0);
		return val;
	}

	/**
	 * Support for inserting an object into the heap.
	 * 
	 * @param e
	 */
	public void add(T e) {
		if (size == values.length) {
			values = Arrays.copyOf(values, values.length * 2);
		}
		values[size] = e;
		heapUp(size++);
	}

	/**
	 * Sample test case
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Create an array of random numbers
		Integer[] vals = new Integer[100];
		for (int idx = 0; idx < 100; idx++) {
			int random = (int) (1 + Math.random() * 99);
			vals[idx] = random;
		}
		// Create a heap of integer type
		MyHeap<Integer> heap = new MyHeap<Integer>(
				vals,
				/* Comparator is defined to create a min heap */new Comparator<Integer>() {
					public int compare(Integer o1, Integer o2) {
						return o1.compareTo(o2);
					}
				});
		heap.heapify();
		heap.add(-1);
		int idx = 0;
		while (heap.size > 0) {
			if (idx > 0 && idx % 5 == 0) {
				System.out.println();
			}
			System.out.print(" [" + idx++ + "]=" + heap.remove());
		}
	}
}
