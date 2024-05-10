import java.util.*;

public class MyHeap<T> {
	private List<T> values;
	private int size;
	private Comparator<T> comp;

	public MyHeap(List<T> vals, Comparator<T> c) {
		this.values = new ArrayList<>(vals);
		this.size = vals.size();
		this.comp = Objects.requireNonNull(c, "Comparator cannot be null");
		this.heapify();
	}

	public MyHeap(Comparator<T> c) {
		this(new ArrayList<>(), c);
	}

	protected int getChildIdx(int idx, int offset) {
		return (2 * idx) + offset;
	}

	protected int getParentIdx(int idx) {
		return (idx - 1) / 2;
	}

	public void heapify() {
		for (int idx = size - 1; idx >= 0; idx--) {
			heapDown(idx);
		}
	}

	protected void heapDown(int idx) {
		int leftChildIdx = getChildIdx(idx, 1);
		int rightChildIdx = getChildIdx(idx, 2);
		if (leftChildIdx >= size) {
			return;
		} else if (rightChildIdx >= size) {
			if (comp.compare(values.get(leftChildIdx), values.get(idx)) < 0) {
				swap(idx, leftChildIdx);
				heapDown(leftChildIdx);
			}
		} else {
			int childIndex = (comp.compare(values.get(leftChildIdx),
					values.get(rightChildIdx)) < 0) ? leftChildIdx : rightChildIdx;
			if (comp.compare(values.get(childIndex), values.get(idx)) < 0) {
				swap(idx, childIndex);
				heapDown(childIndex);
			}
		}
	}

	protected void heapUp(int idx) {
		int parentIdx = getParentIdx(idx);
		if (parentIdx != -1 && comp.compare(values.get(idx), values.get(parentIdx)) < 0) {
			swap(idx, parentIdx);
			heapUp(parentIdx);
		}
	}

	protected void swap(int parentIdx, int idx) {
		T temp = this.values.get(parentIdx);
		this.values.set(parentIdx, this.values.get(idx));
		this.values.set(idx, temp);
	}

	public Optional<T> remove() {
		if (size == 0) {
			return Optional.empty();
		}
		T val = this.values.get(0);
		swap(0, --size);
		heapDown(0);
		return Optional.of(val);
	}

	public void add(T e) {
		Objects.requireNonNull(e, "Element cannot be null");
		this.values.add(e);
		heapUp(size++);
	}

	public static void main(String[] args) {

		MyHeap<Integer> heap = new MyHeap<>(Integer::compare);
		for (int idx = 0; idx < 100; idx++) {
			heap.add((int) (Math.random() * 100));
		}
		int idx = 0;
		while (heap.size > 0) {
			if (idx > 0 && idx % 5 == 0) {
				System.out.println();
			}
			System.out.print(" [" + idx++ + "]=" + heap.remove().orElse(null));
		}
	}
}