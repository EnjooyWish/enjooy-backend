//package com.kibrit.authentication.model;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import javax.persistence.Column;
//import javax.persistence.EntityListeners;
//import javax.persistence.MappedSuperclass;
//import java.time.LocalDateTime;
//
//@Getter(AccessLevel.PROTECTED)
//@Setter(AccessLevel.PROTECTED)
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
//public class Auditable<U>
//{
//    @CreatedBy
//    @Column(name = "created_by")
//    private U createdBy;
//
//    @CreatedDate
//    @Column(name = "creation_date")
//    private LocalDateTime creationDate;
//
//    @LastModifiedBy
//    @Column(name = "last_modified_by")
//    private U lastModifiedBy;
//
//    @LastModifiedDate
//    @Column(name = "last_modification_date")
//    private LocalDateTime lastModificationDate;
//}