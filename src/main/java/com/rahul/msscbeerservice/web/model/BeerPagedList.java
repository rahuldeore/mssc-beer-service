package com.rahul.msscbeerservice.web.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Rahul on 12/15/19
 */
public class BeerPagedList extends PageImpl<BeerDto> implements Serializable {

    static final long serialVersionUID = 6443230264864997894L;
    public BeerPagedList(List<BeerDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BeerPagedList(List<BeerDto> content) {
        super(content);
    }
}
