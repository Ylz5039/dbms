package com.st.parser;

;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * B+ 树的实现，用于索引管理。
 * K 为键类型，必须实现 Comparable 接口。
 * V 为值类型。
 */
public class BPlusTree<K extends Comparable<K>, V> implements Serializable {
    private static final int ORDER = 4; // B+ 树的阶数
    private Node root; // 根节点

    /**
     * 节点类，包括内部节点和叶子节点。
     */
    private abstract class Node implements Serializable {
        List<K> keys;

        Node() {
            this.keys = new ArrayList<>();
        }

        abstract boolean isLeaf();
    }

    /**
     * 叶子节点类，存储键和值，并通过 next 指针链接到下一个叶子节点。
     */
    private class LeafNode extends Node {
        List<V> values;
        LeafNode next;

        LeafNode() {
            super();
            this.values = new ArrayList<>();
            this.next = null;
        }

        @Override
        boolean isLeaf() {
            return true;
        }

        /**
         * 插入键值对到叶子节点。
         */
        void insert(K key, V value) {
            int idx = 0;
            while (idx < keys.size() && key.compareTo(keys.get(idx)) > 0) {
                idx++;
            }
            keys.add(idx, key);
            values.add(idx, value);
        }

        /**
         * 查找键对应的值。
         */
        List<V> search(K key) {
            List<V> result = new ArrayList<>();
            for (int i = 0; i < keys.size(); i++) {
                if (keys.get(i).compareTo(key) == 0) {
                    result.add(values.get(i));
                }
            }
            return result;
        }

        /**
         * 分裂叶子节点，返回新的叶子节点。
         */
        LeafNode split() {
            LeafNode newNode = new LeafNode();
            int from = (keys.size() + 1) / 2;
            int to = keys.size();

            newNode.keys.addAll(keys.subList(from, to));
            newNode.values.addAll(values.subList(from, to));

            keys.subList(from, to).clear();
            values.subList(from, to).clear();

            newNode.next = this.next;
            this.next = newNode;

            return newNode;
        }
    }

    /**
     * 内部节点类，存储键和子节点指针。
     */
    private class InternalNode extends Node {
        List<Node> children;

        InternalNode() {
            super();
            this.children = new ArrayList<>();
        }

        @Override
        boolean isLeaf() {
            return false;
        }

        /**
         * 插入键和子节点。
         */
        void insertChild(K key, Node child) {
            int idx = 0;
            while (idx < keys.size() && key.compareTo(keys.get(idx)) > 0) {
                idx++;
            }
            keys.add(idx, key);
            children.add(idx + 1, child);
        }

        /**
         * 分裂内部节点，返回新的内部节点和中间键。
         */
        SplitResult split() {
            int midIndex = keys.size() / 2;
            K midKey = keys.get(midIndex);

            InternalNode newNode = new InternalNode();
            newNode.keys.addAll(keys.subList(midIndex + 1, keys.size()));
            newNode.children.addAll(children.subList(midIndex + 1, children.size()));

            keys.subList(midIndex, keys.size()).clear();
            children.subList(midIndex + 1, children.size()).clear();

            return new SplitResult(midKey, newNode);
        }
    }

    /**
     * 分裂结果类，包含中间键和新节点。
     */
    private class SplitResult {
        K key;
        Node node;

        SplitResult(K key, Node node) {
            this.key = key;
            this.node = node;
        }
    }

    /**
     * 插入键值对到 B+ 树。
     *
     * @param key   键
     * @param value 值
     */
    public void insert(K key, V value) {
        if (root == null) {
            LeafNode leaf = new LeafNode();
            leaf.insert(key, value);
            root = leaf;
            return;
        }

        SplitResult result = insertRecursive(root, key, value);
        if (result != null) {
            InternalNode newRoot = new InternalNode();
            newRoot.keys.add(result.key);
            newRoot.children.add(root);
            newRoot.children.add(result.node);
            root = newRoot;
        }
    }

    /**
     * 递归插入键值对，返回分裂结果。
     */
    private SplitResult insertRecursive(Node node, K key, V value) {
        if (node.isLeaf()) {
            LeafNode leaf = (LeafNode) node;
            leaf.insert(key, value);
            if (leaf.keys.size() >= ORDER) {
                return new SplitResult(leaf.keys.get(leaf.keys.size() / 2), leaf.split());
            }
            return null;
        } else {
            InternalNode internal = (InternalNode) node;
            int idx = 0;
            while (idx < internal.keys.size() && key.compareTo(internal.keys.get(idx)) > 0) {
                idx++;
            }
            SplitResult result = insertRecursive(internal.children.get(idx), key, value);
            if (result != null) {
                internal.insertChild(result.key, result.node);
                if (internal.keys.size() >= ORDER) {
                    return internal.split();
                }
            }
            return null;
        }
    }

    /**
     * 根据键查找对应的值。
     *
     * @param key 键
     * @return 匹配的值列表
     */
    public List<V> search(K key) {
        if (root == null) {
            return new ArrayList<>();
        }
        return searchRecursive(root, key);
    }


    /**
     * 递归查找键对应的值。
     */
    private List<V> searchRecursive(Node node, K key) {
        if (node.isLeaf()) {
            LeafNode leaf = (LeafNode) node;
            return leaf.search(key);
        } else {
            InternalNode internal = (InternalNode) node;
            int idx = 0;
            while (idx < internal.keys.size() && key.compareTo(internal.keys.get(idx)) > 0) {
                idx++;
            }
            return searchRecursive(internal.children.get(idx), key);
        }
    }

    /**
     * 序列化 B+ 树到输出流。
     *
     * @param out 输出流
     * @throws IOException IO异常
     */
    public void serialize(ObjectOutputStream out) throws IOException {
        writeNode(out, root);
    }

    private void writeNode(ObjectOutputStream out, Node node) throws IOException {
        if (node == null) {
            out.writeBoolean(false);
            return;
        }
        out.writeBoolean(true);
        out.writeBoolean(node.isLeaf());
        out.writeInt(node.keys.size());
        for (K key : node.keys) {
            out.writeObject(key); // 直接序列化键对象
        }
        if (node.isLeaf()) {
            LeafNode leaf = (LeafNode) node;
            for (V value : leaf.values) {
                out.writeObject(value); // 直接序列化值对象
            }
            writeNode(out, leaf.next);
        } else {
            InternalNode internal = (InternalNode) node;
            for (Node child : internal.children) {
                writeNode(out, child);
            }
        }
    }

    /**
     * 从输入流反序列化 B+ 树。
     *
     * @param in 输入流
     * @return 反序列化后的 B+ 树
     * @throws IOException IO异常
     */
    public static <K extends Comparable<K>, V> BPlusTree<K, V> deserialize(ObjectInputStream in) throws IOException, ClassNotFoundException {
        BPlusTree<K, V> tree = new BPlusTree<>();
        tree.root = tree.readNode(in);
        return tree;
    }

    private Node readNode(ObjectInputStream in) throws IOException, ClassNotFoundException {
        boolean isNotNull = in.readBoolean();
        if (!isNotNull) {
            return null;
        }
        boolean isLeaf = in.readBoolean();
        int keySize = in.readInt();
        List<K> keys = new ArrayList<>();
        for (int i = 0; i < keySize; i++) {
            @SuppressWarnings("unchecked")
            K key = (K) in.readObject(); // 反序列化键对象
            keys.add(key);
        }
        if (isLeaf) {
            LeafNode leaf = new LeafNode();
            leaf.keys.addAll(keys);
            for (int i = 0; i < keySize; i++) {
                @SuppressWarnings("unchecked")
                V value = (V) in.readObject(); // 反序列化值对象
                leaf.values.add(value);
            }
            leaf.next = (LeafNode) readNode(in);
            return leaf;
        } else {
            InternalNode internal = new InternalNode();
            internal.keys.addAll(keys);
            for (int i = 0; i < keySize + 1; i++) {
                Node child = readNode(in);
                internal.children.add(child);
            }
            return internal;
        }

    }
    /**
     * 删除键值对
     *
     * @param key 要删除的键
     */
    public void delete(K key) {
        if (root == null) {
            return; // 树为空，直接返回
        }
        deleteRecursive(root, key);

        // 如果根节点为空且根是内部节点，需要调整根节点
        if (root.keys.size() == 0 && !root.isLeaf()) {
            root = ((InternalNode) root).children.get(0);
        }
    }

    /**
     * 递归删除键值对
     */
    private void deleteRecursive(Node node, K key) {
        if (node.isLeaf()) {
            LeafNode leaf = (LeafNode) node;
            int idx = leaf.keys.indexOf(key);
            if (idx != -1) {
                leaf.keys.remove(idx);
                leaf.values.remove(idx);
            }
        } else {
            InternalNode internal = (InternalNode) node;
            int idx = 0;
            while (idx < internal.keys.size() && key.compareTo(internal.keys.get(idx)) > 0) {
                idx++;
            }
            deleteRecursive(internal.children.get(idx), key);

            // 如果子节点为空，合并或借用兄弟节点
            if (internal.children.get(idx).keys.size() < (ORDER + 1) / 2) {
                rebalance(internal, idx);
            }
        }
    }

    /**
     * 调整节点以保持B+树的平衡
     */
    private void rebalance(InternalNode parent, int index) {
        if (index > 0 && parent.children.get(index - 1).keys.size() > (ORDER + 1) / 2) {
            // 从左兄弟借一个键
            Node leftSibling = parent.children.get(index - 1);
            Node current = parent.children.get(index);

            // 处理叶子节点或内部节点的借用逻辑
        } else if (index < parent.children.size() - 1 && parent.children.get(index + 1).keys.size() > (ORDER + 1) / 2) {
            // 从右兄弟借一个键
            Node rightSibling = parent.children.get(index + 1);
            Node current = parent.children.get(index);

            // 处理叶子节点或内部节点的借用逻辑
        } else {
            // 合并节点
            if (index > 0) {
                // 与左兄弟合并
                Node leftSibling = parent.children.get(index - 1);
                Node current = parent.children.get(index);

                // 合并逻辑
            } else {
                // 与右兄弟合并
                Node rightSibling = parent.children.get(index + 1);
                Node current = parent.children.get(index);

                // 合并逻辑
            }
        }
    }
    /**
     * 更新键值对
     *
     * @param key   要更新的键
     * @param value 新的值
     */
    public void update(K key, V value) {
        delete(key); // 删除旧的键值对
        insert(key, value); // 插入新的键值对
    }


}
