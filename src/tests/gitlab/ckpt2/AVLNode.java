package tests.gitlab.ckpt2;

public class AVLNode<K,V> {
    public K key;
    public V value;
    public int height;
    public AVLNode parent;

    public AVLNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.height = 0;
        this.parent = null;
    }

}
