public class MyHashMap<K, V> {

    // Внутренний класс для хранения данных
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V>[] buckets;
    private int capacity = 16; // Начальный размер массива
    private int size = 0;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        this.buckets = new Node[capacity];
    }

    // Хеш-функция для вычисления индекса
    private int getBucketIndex(K key) {
        if (key == null) return 0;
        return Math.abs(key.hashCode()) % capacity;
    }

    // МЕТОД PUT: Добавление или обновление
    public void put(K key, V value) {
        int index = getBucketIndex(key);
        Node<K, V> head = buckets[index];

        // Проверяем, есть ли уже такой ключ в списке
        while (head != null) {
            if (head.key != null && head.key.equals(key)) {
                head.value = value; // Обновляем значение
                return;
            }
            head = head.next;
        }

        // Если ключа нет, добавляем новый узел в начало списка (Bucket)
        size++;
        head = buckets[index];
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = head;
        buckets[index] = newNode;
    }

    // МЕТОД GET: Поиск значения
    public V get(K key) {
        int index = getBucketIndex(key);
        Node<K, V> head = buckets[index];

        while (head != null) {
            if ((key == null && head.key == null) || (key != null && key.equals(head.key))) {
                return head.value;
            }
            head = head.next;
        }
        return null; // Если ключ не найден
    }

    // МЕТОД REMOVE: Удаление по ключу
    public V remove(K key) {
        int index = getBucketIndex(key);
        Node<K, V> head = buckets[index];
        Node<K, V> prev = null;

        while (head != null) {
            if ((key == null && head.key == null) || (key != null && key.equals(head.key))) {
                break;
            }
            prev = head;
            head = head.next;
        }

        if (head == null) return null; // Ключ не найден

        size--;
        if (prev != null) {
            prev.next = head.next; // Убираем узел из середины или конца
        } else {
            buckets[index] = head.next; // Убираем первый узел
        }

        return head.value;
    }

    public int size() {
        return size;
    }
}
