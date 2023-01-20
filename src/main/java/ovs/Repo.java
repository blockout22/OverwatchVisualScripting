package ovs;

import java.util.ArrayList;

public class Repo {
    public String url;
    public String assets_url;
    public String upload_url;
    public String html_url;
    public String id;

    public Author author;

    public String node_id;
    public String tag_name;
    public String target_commitish;
    public String name;
    public String draft;
    public String prerelease;
    public String created_at;
    public String published_at;

    public ArrayList<Assets> assets;

    public String tarball_url;
    public String zipball_url;
    public String body;

    public class Author {

        public String login;
        public String id;
        public String node_id;
        public String avatar_url;
        public String gravatar_id;
        public String url;
        public String html_url;
        public String followers_url;
        public String following_url;
        public String gists_url;
        public String starred_url;
        public String subscriptions_url;
        public String organizations_url;
        public String repos_url;
        public String events_url;
        public String received_events_url;
        public String type;
        public String site_admin;
    }

    public class Assets{
        public String url;
        public String id;
        public String node_id;
        public String name;
        public String label;
        public Uploader uploader;
        public String content_type;
        public String state;
        public String size;
        public String download_count;
        public String created_at;
        public String updated_at;
        public String browser_download_url;

        public class Uploader{
            public String login;
            public String id;
            public String node_id;
            public String avatar_url;
            public String gravatar_id;
            public String url;
            public String html_url;
            public String followers_url;
            public String following_url;
            public String gists_url;
            public String starred_url;
            public String subscriptions_url;
            public String organizations_url;
            public String repos_url;
            public String events_url;
            public String received_events_url;
            public String type;
            public String site_admin;
        }
    }
}
