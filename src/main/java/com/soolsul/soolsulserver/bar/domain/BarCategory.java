package com.soolsul.soolsulserver.bar.domain;

import com.soolsul.soolsulserver.common.domain.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BarCategory extends Category {}