INSERT INTO diseases(disease_name, created_at, created_by)
VALUES ("변비", now(), "hyensu"),
       ("과민성장증후군_설사형", now(), "hyensu"),
       ("과민성장증후군_변비형", now(), "hyensu"),
       ("치질", now(), "hyensu"),
       ("치핵", now(), "hyensu"),
       ("치열", now(), "hyensu"),
       ("변실금", now(), "hyensu"),
       ("항문소양증", now(), "hyensu"),
       ("대장암", now(), "hyensu"),
       ("크론병", now(), "hyensu"),
       ("궤양성대장염", now(), "hyensu"),
       ("복부팽만", now(), "hyensu"),
       ("없음", now(), "hyensu")
;

INSERT INTO categories(category_name, created_at, created_by)
VALUES ("유산균", now(), "hyensu"),
       ("식이섬유", now(), "hyensu"),
       ("저포드맵", now(), "hyensu"),
       ("비건", now(), "hyensu"),
       ("기타_장건강_간식", now(), "hyensu")
;

INSERT INTO boards(board_name)
VALUES ("자유"),
       ("변비"),
       ("과민성장증후군_설사형"),
       ("과민성장증후군_변비형"),
       ("치질"),
       ("치핵"),
       ("치열"),
       ("변실금"),
       ("항문소양증"),
       ("대장암"),
       ("크론병"),
       ("궤양성대장염"),
       ("복부팽만"),
       ("기타")
;

INSERT INTO products(product_name, product_comment, product_sale_link, product_regular_price,
                     product_profile, created_at, created_by)
VALUES ("상품1", "코멘트1", "판매 링크", 10000, "프로필 이미지", now(), "hyensu"),
       ("상품2", "코멘트2", "판매 링크", 10000, "프로필 이미지", now(), "hyensu"),
       ("상품3", "코멘트3", "판매 링크", 10000, "프로필 이미지", now(), "hyensu"),
       ("상품4", "코멘트4", "판매 링크", 10000, "프로필 이미지", now(), "hyensu"),
       ("상품5", "코멘트5", "판매 링크", 10000, "프로필 이미지", now(), "hyensu"),
       ("상품6", "코멘트6", "판매 링크", 10000, "프로필 이미지", now(), "hyensu"),
       ("상품7", "코멘트7", "판매 링크", 10000, "프로필 이미지", now(), "hyensu"),
       ("상품8", "코멘트8", "판매 링크", 10000, "프로필 이미지", now(), "hyensu"),
       ("상품9", "코멘트9", "판매 링크", 10000, "프로필 이미지", now(), "hyensu");
