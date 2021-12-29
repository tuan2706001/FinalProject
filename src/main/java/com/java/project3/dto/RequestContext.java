package com.java.project3.dto;


import com.java.project3.domain.Ministry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestContext {
    private Ministry ministry;
    private Long masterUnitId;
    private boolean isDomain;
    private boolean isGuest;

    public RequestContext(Ministry ministry) {
        this.ministry = ministry;
    }
}
