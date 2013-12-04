package com.whoshuu.artbox.artemis.utils;

public interface ImmutableBag<E> {

	E get(int index);

	int size();

	boolean isEmpty();

}
