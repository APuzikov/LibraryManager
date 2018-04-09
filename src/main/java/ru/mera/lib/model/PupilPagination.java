package ru.mera.lib.model;

import ru.mera.lib.entity.Pupil;

import java.util.List;

public class PupilPagination {

    private List<Pupil> pupils;
    private int pageCount;

    public PupilPagination(List<Pupil> pupils, int pageCount) {
        this.pupils = pupils;
        this.pageCount = pageCount;
    }

    public List<Pupil> getPupils() {
        return pupils;
    }

    public void setPupils(List<Pupil> pupils) {
        this.pupils = pupils;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
