package com.pactera.chengguan.municipal.model.municipal;

import java.util.List;

/**
 * 考核库
 * Created by huang hua
 * 2016/4/12.
 */
public class CheckLib {

    private int id;
    private String name;
    private List<Content> contents;

    public static class Content{
        private int id;
        private String content;
        private String points;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
}
