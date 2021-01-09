import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        Arrays.fill(storage, size, 0, null);
        size = 0;
    }

    void save(Resume resume) {
        if (size == storage.length) {
            System.out.println("Resume storage is full. Сan't save the resume");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int uuidIndex = -1;

        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                uuidIndex = i;
            }
        }

        if (uuidIndex != -1) {
            storage[uuidIndex] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        if (size == 0) {
            return null;
        }
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    int size() {
        return size;
    }
}
