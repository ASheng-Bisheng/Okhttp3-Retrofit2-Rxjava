package cn.whzwl.xbs.network.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:ZWLRobot_WDJ
 * <p>
 * 版权：智味来 版权所有
 * <p>
 * 作者：ASheng
 * <p>
 * 创建日期：2019/3/18 12:02
 * <p>
 * 描述：
 */

public class Foods implements Serializable {

    List<food> foods = new ArrayList<>();

    public List<food> getFoods() {
        return foods;
    }

    public void setFoods(List<food> foods) {
        this.foods = foods;
    }

    public static class food implements Serializable {

        private String id;
        private String food_name;
        private String food_step;
        private String food_img;
        private String food_thumb;
        private String addtime;
        private String isdel;
        private String syn;

        @Override
        public String toString() {
            return "food{" +
                    "id='" + id + '\'' +
                    ", food_name='" + food_name + '\'' +
                    ", food_step='" + food_step + '\'' +
                    ", food_img='" + food_img + '\'' +
                    ", food_thumb='" + food_thumb + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", isdel='" + isdel + '\'' +
                    ", syn='" + syn + '\'' +
                    '}';
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setFood_name(String food_name) {
            this.food_name = food_name;
        }

        public String getFood_name() {
            return food_name;
        }

        public void setFood_step(String food_step) {
            this.food_step = food_step;
        }

        public String getFood_step() {
            return food_step;
        }

        public void setFood_img(String food_img) {
            this.food_img = food_img;
        }

        public String getFood_img() {
            return food_img;
        }

        public void setFood_thumb(String food_thumb) {
            this.food_thumb = food_thumb;
        }

        public String getFood_thumb() {
            return food_thumb;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setIsdel(String isdel) {
            this.isdel = isdel;
        }

        public String getIsdel() {
            return isdel;
        }

        public void setSyn(String syn) {
            this.syn = syn;
        }

        public String getSyn() {
            return syn;
        }


    }


}