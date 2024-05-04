package com.zerocoder.devsearch.utils;

import com.zerocoder.devsearch.entity.Profile;

import java.util.List;

public class SearchProfile {
    private List<Profile> profiles;
    private int totalCount;
    private int totalPages;
    private int currentPage;

    public SearchProfile() {
    }

    public SearchProfile(List<Profile> profiles, int totalCount, int totalPages, int currentPage) {
        this.profiles = profiles;
        this.totalCount = totalCount;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
