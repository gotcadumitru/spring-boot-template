package com.dima.demo.storage;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String idFromStorage;
    private String mimetype;
    private String fileUrl;
    private String downloadUrl;
    private Long sizeInBytes;

    public Storage(String name, String idFromStorage, String mimetype, String fileUrl, String downloadUrl, Long sizeInBytes) {
        this.name = name;
        this.idFromStorage = idFromStorage;
        this.mimetype = mimetype;
        this.fileUrl = fileUrl;
        this.downloadUrl = downloadUrl;
        this.sizeInBytes = sizeInBytes;
    }
}
