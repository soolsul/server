package com.soolsul.soolsulserver.bar.domain;

import com.soolsul.soolsulserver.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BarAlcoholTag extends BaseEntity {

    @Column(nullable = false)
    private String name;

    public BarAlcoholTag(String name) {
        this.name = name;
    }
}
