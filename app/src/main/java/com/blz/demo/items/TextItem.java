package com.blz.demo.items;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 卜令壮
 * on 2015/12/29
 * E-mail q137617549@qq.com
 */
public class TextItem implements Serializable{

    /**
     * format : word
     * image : null
     * published_at : 1451360103
     * tag :
     * user : {"avatar_updated_at":1404863863,"last_visited_at":1390996170,"created_at":1390996170,"state":"bonded","email":"1282563353@qq.com","last_device":"android_2.7","role":"n","login":"天下第八百六十一帅","id":13865837,"icon":"nopic.jpg"}
     * image_size : null
     * id : 114464686
     * votes : {"down":-121,"up":4988}
     * created_at : 1451358753
     * content : 本人小女子一枚，回家后，把内衣里的胸垫随便的丢在了桌上。老妈看见后就问我这是什么东西？我不屑的说：“作为一个女人，居然连这都不知道！”结果换来我妈一句：“作为一个女人还用这玩意？！”口气超鄙视！我瞬间觉得被我妈秒杀了!
     * state : publish
     * comments_count : 58
     * allow_comment : true
     * share_count : 44
     * type : hot
     */

    private List<ItemsEntity> items;

    public void setItems(List<ItemsEntity> items) {
        this.items = items;
    }

    public List<ItemsEntity> getItems() {
        return items;
    }

    public static class ItemsEntity implements Serializable {
        //使用混淆的标准做法，所有的都加上注解
        private String high_url;
        private String format;
        private String image;

        /**
         * avatar_updated_at : 1404863863
         * last_visited_at : 1390996170
         * created_at : 1390996170
         * state : bonded
         * email : 1282563353@qq.com
         * last_device : android_2.7
         * role : n
         * login : 天下第八百六十一帅
         * id : 13865837
         * icon : nopic.jpg
         */

        private UserEntity user;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        /**
         * down : -121
         * up : 4988
         */

        private VotesEntity votes;
        private String content;
        private String pic_url;
        private int comments_count;
        private String low_url;

        public String getHigh_url() {
            return high_url;
        }

        public void setHigh_url(String high_url) {
            this.high_url = high_url;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getLow_url() {
            return low_url;
        }

        public void setLow_url(String low_url) {
            this.low_url = low_url;
        }

        private int share_count;
        private String type;

        public void setFormat(String format) {
            this.format = format;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public void setVotes(VotesEntity votes) {
            this.votes = votes;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setComments_count(int comments_count) {
            this.comments_count = comments_count;
        }

        public void setShare_count(int share_count) {
            this.share_count = share_count;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFormat() {
            return format;
        }

        public String getImage() {
            return image;
        }

        public UserEntity getUser() {
            return user;
        }

        public VotesEntity getVotes() {
            return votes;
        }

        public String getContent() {
            return content;
        }

        public int getComments_count() {
            return comments_count;
        }

        public int getShare_count() {
            return share_count;
        }

        public String getType() {
            return type;
        }

        public static class UserEntity implements Serializable{
            private String login;
            private int id;
            private String icon;

            public void setLogin(String login) {
                this.login = login;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getLogin() {
                return login;
            }

            public int getId() {
                return id;
            }

            public String getIcon() {
                return icon;
            }
        }

        public static class VotesEntity implements Serializable {
            private int down;
            private int up;

            public void setDown(int down) {
                this.down = down;
            }

            public void setUp(int up) {
                this.up = up;
            }

            public int getDown() {
                return down;
            }

            public int getUp() {
                return up;
            }
        }
    }
}
