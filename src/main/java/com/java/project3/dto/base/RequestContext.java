package com.java.project3.dto.base;


import com.java.project3.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestContext {
    private User user;
    private Long masterUnitId;
    private boolean isDomain;
    private boolean isGuest;

    public RequestContext(User user) {
        this.user = user;
    }
}
