public class Halda<T extends Comparable<T>> {

	private BobLink<T> Items = new BobLink<T>();

	public Halda() {
	}

	public Halda(Halda<T> h) {
		Items = h.Items;
	}

	private int getParentIndex(int childIndex) {
		if (childIndex % 2 == 0) {
			return (childIndex - 1) / 2;
		}
		return childIndex / 2;
	}

	private int getLeftChildIndex(int parentIndex) {
		return 2 * parentIndex + 1;
	}

	private int getRightChildIndex(int parentIndex) {
		return 2 * parentIndex + 2;
	}

	private int getSmalerChildIndex(int parentIndex) {
		if (getLeftChildIndex(parentIndex) >= Items.size() && getRightChildIndex(parentIndex) >= Items.size()) {
			return -1;
		} else if (getLeftChildIndex(parentIndex) >= Items.size()) {
			return getRightChildIndex(parentIndex);
		} else if (getRightChildIndex(parentIndex) >= Items.size()) {
			return getLeftChildIndex(parentIndex);
		} else if (Items.get(getLeftChildIndex(parentIndex))
				.compareTo(Items.get(getRightChildIndex(parentIndex))) < 0) {
			return getLeftChildIndex(parentIndex);
		} else {
			return getRightChildIndex(parentIndex);
		}
	}

	public void add(T item) {
		Items.add(item);
		int pom = Items.size() - 1;
		while (pom != 0 && Items.get(pom).compareTo(Items.get(getParentIndex(pom))) < 0) {
			T pomm = Items.get(getParentIndex(pom));
			Items.set(getParentIndex(pom), Items.get(pom));
			Items.set(pom, pomm);
			pom = getParentIndex(pom);
		}
	}

	public void addAll(T... items) {
		for (int i = 0; i < items.length; i++) {
			add(items[i]);
		}
	}

	public int size() {
		return Items.size();
	}

	public T min() {
		return get(0);

	}

	public T get(int index) {
		if (Items.size() != 0) {
			return Items.get(index);
		} else {
			System.err.println("Can not get from empty Halda!");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public T extractMin() {
		if (Items.size() != 0) {
			T pom = Items.get(0);
			Items.set(0, Items.get(Items.size() - 1));
			Items.remove(Items.size() - 1);
			int aktualni = 0;
			while (getSmalerChildIndex(aktualni) != -1
					&& Items.get(aktualni).compareTo(Items.get(getSmalerChildIndex(aktualni))) > 0) {
				int pommm = getSmalerChildIndex(aktualni);
				T pomm = Items.get(getSmalerChildIndex(aktualni));
				Items.set(getSmalerChildIndex(aktualni), Items.get(aktualni));
				Items.set(aktualni, pomm);
				aktualni = pommm;
			}
			return pom;
		} else {
			System.err.println("Can not remove from empty Halda!");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	public String toString() {
		String vypis = "";
		int i = 0;
		int vrstvy = 0;
		while (i < Items.size()) {
			i = (i + 1) * 2 - 1;
			vrstvy++;
		}
		i = (int) Math.pow(2, vrstvy);
		int pom = 0;
		for (int a = vrstvy; a > 0; a--) {
			for (int j = 0; j < Math.pow(2, vrstvy - a); j++) {
				if (j == 0) {
					for (int h = 0; h < i / 2; h++) {
						vypis += "\t";
					}
				} else {
					for (int h = 0; h < i; h++) {
						vypis += "\t";
					}
				}
				if (pom < Items.size()) {
					vypis += Items.get(pom);
				}
				pom++;
			}
			i /= 2;
			vypis += System.lineSeparator();
		}
		return vypis;
	}

}