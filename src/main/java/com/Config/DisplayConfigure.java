package com.Config;

public class DisplayConfigure {
    private int CountPerPage;
    private int userId;

    public DisplayConfigure() {}


    public int getCountPerPage() {
        return CountPerPage;
    }

    public int getUserId() {
        return userId;
    }

    public void setCountPerPage(int countPerPage) {
        CountPerPage = countPerPage;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DisplayConfigure{" +
                "CountPerPage=" + CountPerPage +
                ", userId=" + userId +
                '}';
    }
}
