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
VALUES ("추석선물세트 푸룬 건자두 2구 선물세트 넛츠앤 37호", "코멘트1", "판매 링크", 10000, "프로필 이미지", now(), "hyensu"),
       ("프로바이오틱스 유산균19 30포(2개) MN970254 [원산지:국산]", "코멘트2", "판매 링크", 10000, "프로필 이미지", now(),
        "hyensu"),
       ("커클랜드 그릭 요거트 플레인 논팻 907g [원산지:미국]", "코멘트3", "판매 링크", 10000, "프로필 이미지", now(), "hyensu"),
       ("KAMUT 골드 카무트 효소 3g x 30포 1통 [원산지:상세설명에 표시]", "코멘트4", "판매 링크", 10000, "프로필 이미지", now(),
        "hyensu"),
       ("Brelat 브렐렛 모짜렐라 치즈 125g [원산지:상세설명에 표시]", "코멘트5", "판매 링크", 10000, "프로필 이미지", now(),
        "hyensu"),
       ("버터넛 마운틴 팜 메이플 시럽 237ml [원산지:미국]", "코멘트6", "판매 링크", 10000, "프로필 이미지", now(), "hyensu"),
       ("상품7", "코멘트7", "판매 링크", 10000, "프로필 이미지", now(), "hyensu"),
       ("상품8", "코멘트8", "판매 링크", 10000, "프로필 이미지", now(), "hyensu"),
       ("상품9", "코멘트9", "판매 링크", 10000, "프로필 이미지", now(), "hyensu");
