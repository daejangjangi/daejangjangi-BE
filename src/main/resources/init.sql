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
       ("과민성장증후군설사형"),
       ("과민성장증후군변비형"),
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
