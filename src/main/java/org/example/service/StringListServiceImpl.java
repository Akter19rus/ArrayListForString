package org.example.service;

import org.example.exceptions.FullException;
import org.example.exceptions.InvalidIndexException;
import org.example.exceptions.NullItemException;

import java.util.Arrays;

public class StringListServiceImpl implements StringListService {
    private final String[] massive;
    private Integer size;

    public StringListServiceImpl() {
        massive = new String[10];
    }

    public StringListServiceImpl(Integer initSize) {
        massive = new String[initSize];
    }

    @Override
    public String add(String item) {
        validateSize();
        validateItem(item);
        massive[size++] = item;
        return item;
    }

    @Override
    public String add(int index, String item) {
        validateSize();
        validateIndex(index);
        validateItem(item);
        if (index == size) {
            massive[size++] = item;
            return item;
        }
        System.arraycopy(
                massive, index, massive,
                index + 1, size - index
        );
        massive[index] = item;
        size++;
        return item;
    }

    @Override
    public String set(int index, String item) {
        validateIndex(index);
        validateItem(item);
        massive[index] = item;
        return item;
    }

        @Override
    public String remove(String item) {
        validateItem(item);
        int index = indexOf(item);

        return remove(index);
    }

    @Override
    public String remove(int index) {
        validateIndex(index);
        String item = massive[index];
        if (index != size) {
            System.arraycopy(
                    massive, index + 1,
                    massive, index, size - (index + 1)
            );
        }
        size--;
        return item;
    }

    @Override
    public boolean contains(String item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(String item) {
        for (int i = 0; i < size; i++) {
            if (massive[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {
        for (int i = size - 1; i >= 0; i--) {
            if (massive[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String get(int index) {
        validateIndex(index);
        return massive[index];
    }

    @Override
    public boolean equals(StringListService otherList) {
        return Arrays.equals(this.toArray(), otherList.toArray());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public String[] toArray() {
        return Arrays.copyOf(massive, size);
    }

    private void validateItem(String item) {
        if (item == null) {
            throw new NullItemException();
        }
    }

    private void validateSize() {
        if (size == massive.length) {
            throw new FullException();
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new InvalidIndexException();
        }
    }
}