//package com.java.project3.security;
//
//import com.java.project3.constant.EntityName;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.time.LocalDateTime;
//
//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity(name = EntityName.PERSISTENT_LOGINS)
//@Table(name = EntityName.PERSISTENT_LOGINS)
//public class PersistentLogins {
//    @Id
//    @Column(name = "series", nullable = false, length = 64)
//    private String series;
//
//    @Column(name = "username", nullable = false, length = 64)
//    private String username;
//
//    @Column(name = "token", nullable = false, length = 64)
//    private String token;
//
//    @Column(name = "last_used", nullable = false)
//    private LocalDateTime lastUsed;
//}
