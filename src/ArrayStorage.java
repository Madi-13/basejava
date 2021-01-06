import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int currentLength = 0;

    void clear() {
        Arrays.fill(storage, null);
        currentLength = 0;
    }

    void save(Resume r) {
        if (currentLength == storage.length) {
            System.out.println("Resume storage is full. Сan not save the resume");
        } else {
           storage[currentLength] = r;
           currentLength++;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < currentLength; i++) {
            if (storage[i].toString().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int uuidIndex = -1;

        for (int i = 0; i < currentLength; i++) {
            if (storage[i].toString().equals(uuid)) {
                uuidIndex = i;
            }
        }

        if (uuidIndex != -1) {
            storage[uuidIndex] = storage[currentLength - 1];
            storage[currentLength - 1] = null;
            currentLength--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        if (currentLength == 0) {
            return null;
        }
        Resume[] resumes = new Resume[currentLength];
        System.arraycopy(storage, 0, resumes, 0, currentLength);
        return resumes;
    }

    int size() {
        return currentLength;
    }
}
