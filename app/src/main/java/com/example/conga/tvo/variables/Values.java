package com.example.conga.tvo.variables;

import com.example.conga.tvo.R;
import com.example.conga.tvo.models.RssItem;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ConGa on 12/04/2016.
 */
public class Values {

    public static String[] PAYERS = {"VN EXPRESS", "24H.COM.VN", "DÂN TRÍ", "VIETNAMNET"
    };

    public static int[] ICON_PAYER =
            {R.drawable.vnexpress, R.drawable.haibongio,
                    R.drawable.dantri, R.drawable.vnn
            };
    //key to handle getextras();
    public static final String paper = "paper";
    public static final String category = "category";
    public static final String key = "key";
    public static final String position = "position";
    //background for vn express
    public static int[] BACKGROUND_PAYERS =
            {
                    R.drawable.trangchu, R.drawable.thoisu, R.drawable.thegioi,
                    R.drawable.phapluat
                    , R.drawable.sports, R.drawable.kinhdoanh
                    , R.drawable.khoahoc,
                    R.drawable.xe, R.drawable.giaitri, R.drawable.sohoa,
                    R.drawable.suckhoe,
                    R.drawable.congdong,
                    R.drawable.dulich1, R.drawable.tamsu,
                    R.drawable.cuoi
            };
    //
    //Vnexpress
    public static final String[] VNEXPRESS_CATEGORIES = {"TRANG CHỦ", "THỜI SỰ", "THẾ GIỚI",
            "PHÁP LUẬT",
            "THỂ THAO", "KINH DOANH", "KHOA HỌC", "XE", "GIẢI TRÍ", "SỐ HÓA", "SỨC KHỎE", "CỘNG ĐỒNG", "DU LỊCH"
            , "TÂM SỰ", "CƯỜI"};

    public static final String[] VNEXPRESS_LINKS = {
            "http://vnexpress.net/rss/tin-moi-nhat.rss",
            "http://vnexpress.net/rss/thoi-su.rss",
            "http://vnexpress.net/rss/the-gioi.rss",
            "http://vnexpress.net/rss/phap-luat.rss",
            "http://vnexpress.net/rss/the-thao.rss",
            "http://vnexpress.net/rss/kinh-doanh.rss",
            "http://vnexpress.net/rss/khoa-hoc.rss",
            "http://vnexpress.net/rss/oto-xe-may.rss",
            "http://vnexpress.net/rss/giai-tri.rss",
            "http://vnexpress.net/rss/so-hoa.rss",
            "http://vnexpress.net/rss/suc-khoe.rss",
            "http://vnexpress.net/rss/cong-dong.rss",
            "http://vnexpress.net/rss/du-lich.rss",
            "http://vnexpress.net/rss/tam-su.rss",
            "http://vnexpress.net/rss/cuoi.rss"
    };

    // 24h.com.vn
// image for payers 24h.com .
    public static int[] BACKGROUND_24H_PAYER =
            {
                    R.drawable.newstoday, R.drawable.football, R.drawable.anninh,
                    R.drawable.fashion
                    , R.drawable.hitech, R.drawable.batdongsan
                    , R.drawable.amthuc,
                    R.drawable.lamdep, R.drawable.film, R.drawable.duhoc,
                    R.drawable.tamsu,
                    R.drawable.music,
                    R.drawable.phithoung, R.drawable.it,
                    R.drawable.xe,R.drawable.thitruong, R.drawable.dulich1,
                    R.drawable.suckhoe, R.drawable.cuoi24h, R.drawable.thegioi,
                    R.drawable.showbiz, R.drawable.giaitri
            };
    // category 24h.com.vn

    public static String[] HAIBONGIO_CATEGORIES=
        {
                "TIN TỨC TRONG NGÀY ", "BÓNG ĐÁ" ,"AN NINH - HÌNH SỰ ", "THỜI TRANG",
                "THỜI TRANG HI-TECH", "TÀI CHÍNH-BẤT ĐỘNG SẢN",
                "ẨM THỰC" , "LÀM ĐẸP" ,"PHIM", "GIÁO DỤC - DU HỌC" ,
                "BẠN TRẺ-CUỘC SỐNG", "CA NHẠC -MTV", "PHI THƯỜNG -KÌ QUẶC",
                "CÔNG NGHỆ THÔNG TIN", "Ô TÔ - XE MÁY", "THỊ TRƯỜNG - TIÊU DÙNG",
                "DU LỊCH"  ,"CƯỜI 24H" , "THẾ GIỚI",
                "ĐỜI SỐNG SHOWBIZ" , " GIẢI TRÍ"
        };
    // LINK 24H.COM.VN
    public static String[] HAIBONGIO_LINKS =
            {
                    "http://www.24h.com.vn/upload/rss/tintuctrongngay.rss",
                    "http://www.24h.com.vn/upload/rss/bongda.rss",
                    "http://www.24h.com.vn/upload/rss/anninhhinhsu.rss",
                    "http://www.24h.com.vn/upload/rss/thoitrang.rss",
                    "http://www.24h.com.vn/upload/rss/thoitranghitech.rss",
                    "http://www.24h.com.vn/upload/rss/taichinhbatdongsan.rss",
                    "http://www.24h.com.vn/upload/rss/amthuc.rss",
                    "http://www.24h.com.vn/upload/rss/lamdep.rss",
                    "http://www.24h.com.vn/upload/rss/phim.rss",
                    "http://www.24h.com.vn/upload/rss/giaoducduhoc.rss",
                    "http://www.24h.com.vn/upload/rss/bantrecuocsong.rss",
                    "http://www.24h.com.vn/upload/rss/canhacmtv.rss",
                    "http://www.24h.com.vn/upload/rss/thethao.rss",
                    "http://www.24h.com.vn/upload/rss/phithuongkyquac.rss",
                    "http://www.24h.com.vn/upload/rss/congnghethongtin.rss",
                    "http://www.24h.com.vn/upload/rss/otoxemay.rss",
                    "http://www.24h.com.vn/upload/rss/thitruongtieudung.rss",
                    "http://www.24h.com.vn/upload/rss/dulich.rss",
                    "http://www.24h.com.vn/upload/rss/cuoi24h.rss",
                    "http://www.24h.com.vn/upload/rss/tintucquocte.rss",
                    "http://www.24h.com.vn/upload/rss/doisongshowbiz.rss",
                    "http://www.24h.com.vn/upload/rss/giaitri.rss"

            };
// Dan tri
    // category image Dantri

        public static final int[] BACKGROUNDS_DANTRI ={
        R.drawable.trangchu, R.drawable.suckhoe, R.drawable.xahoi, R.drawable.giaitri,
                R.drawable.education, R.drawable.sports, R.drawable.thegioi,
                R.drawable.kinhdoanh, R.drawable.xe, R.drawable.sohoa,
                R.drawable.tinhyeu, R.drawable.phithoung, R.drawable.job,
                R.drawable.nhipsongtre, R.drawable.tamlongnhanai, R.drawable.phapluat,
                R.drawable.tamsu, R.drawable.tuyensinh, R.drawable.blog,
                R.drawable.vanhoa, R.drawable.duhoc, R.drawable.dulich1, R.drawable.congdong
    };
    //Categories Dantri
    public static final  String[] DANTRI_CATEGORIES={
            "TRANG CHỦ" , "SỨC KHỎE" ,"XÃ HỘI" ,"GIẢI TRÍ","GIÁO DỤC -KHUYẾN HỌC",
            "THỂ THAO" ,"THẾ GIỚI" ,"KINH DOANH" ,"Ô TÔ - XE MÁY" ,"SỨC MẠNH SỐ",
            "TÌNH YÊU - GIỚI TÍNH ", "CHUYỆN LẠ" ,"VIỆC LÀM" ,"NHỊP SỐNG TRẺ",
            "TẤM LÒNG NHÂN ÁI", "PHÁP LUẬT","BẠN ĐỌC","TUYỂN SINH",
            "BLOG", "VĂN HÓA","DU HỌC", "DU LỊCH" ,"ĐỜI SỐNG"
    };
    // links Dantri
   public static final String[] DANTRI_LINKS={
"http://dantri.com.vn/trangchu.rss",
            "http://dantri.com.vn/suc-khoe.rss",
            "http://dantri.com.vn/xa-hoi.rss",
            "http://dantri.com.vn/giai-tri.rss",
            "http://dantri.com.vn/giao-duc-khuyen-hoc.rss",
            "http://dantri.com.vn/the-thao.rss",
            "http://dantri.com.vn/the-gioi.rss",
            "http://dantri.com.vn/kinh-doanh.rss",
            "http://dantri.com.vn/o-to-xe-may.rss",
            "http://dantri.com.vn/suc-manh-so.rss",
            "http://dantri.com.vn/tinh-yeu-gioi-tinh.rss",
            "http://dantri.com.vn/chuyen-la.rss",
            "http://dantri.com.vn/viec-lam.rss",
            "http://dantri.com.vn/nhip-song-tre.rss",
            "http://dantri.com.vn/tam-long-nhan-ai.rss",
            "http://dantri.com.vn/phap-luat.rss",
            "http://dantri.com.vn/ban-doc.rss",
            "http://dantri.com.vn/tuyen-sinh.rss",
            "http://dantri.com.vn/blog.rss",
            "http://dantri.com.vn/van-hoa.rss",
            "http://dantri.com.vn/du-hoc.rss",
            "http://dantri.com.vn/du-lich.rss",
            "http://dantri.com.vn/doi-song.rss"
    };

   // VietNamNet
    // image VIETNAMENET PAYERS
    public static int BACKGROUNDS_VIETNAMNET[] ={
           R.drawable.trangchu, R.drawable.xahoi, R.drawable.technology,
           R.drawable.kinhdoanh, R.drawable.education, R.drawable.giaitri,
           R.drawable.suckhoe, R.drawable.sports, R.drawable.congdong, R.drawable.thegioi,
           R.drawable.batdongsan, R.drawable.tamsu, R.drawable.tinoibat,R.drawable.newstoday, R.drawable.gocvietnam,
           R.drawable.gocnhinthang
   };
    //VietNamNet categories
    public static  String[] VIETNAMNET_CATEGORIES={
"TRANG CHỦ", "XÃ HỘI" , "CÔNG NGHỆ" , "KINH DOANH", "GIÁO DỤC"
            ,"GIẢI TRÍ" , "SỨC KHỎE", "THỂ THAO", "ĐỜI SỐNG "
           ,"THẾ GIỚI", "BẤT ĐỘNG SẢN" ,"BẠN ĐỌC","TIN MỚI NHẤT" ,"TIN NỔI BẬT",
           "TUẦN VIỆT NAM", "GÓC NHÌN THẲNG"
   };
    //VietNamNet Links
    public static String[] VIETNAMNET_LINKS={
            "http://vietnamnet.vn/rss/home.rss",
            "http://vietnamnet.vn/rss/xa-hoi.rss",
            "http://vietnamnet.vn/rss/cong-nghe.rss",
            "http://vietnamnet.vn/rss/kinh-doanh.rss",
            "http://vietnamnet.vn/rss/giao-duc.rss",
            "http://vietnamnet.vn/rss/giai-tri.rss",
            "http://vietnamnet.vn/rss/suc-khoe.rss",
            "http://vietnamnet.vn/rss/the-thao.rss",
            "http://vietnamnet.vn/rss/doi-song.rss",
            "http://vietnamnet.vn/rss/the-gioi.rss",
            "http://vietnamnet.vn/rss/bat-dong-san.rss",
            "http://vietnamnet.vn/rss/ban-doc.rss",
            "http://vietnamnet.vn/rss/moi-nong.rss",
            "http://vietnamnet.vn/rss/tin-noi-bat.rss",
            "http://vietnamnet.vn/rss/tuanvietnam.rss",
            "http://vietnamnet.vn/rss/goc-nhin-thang.rss"
    };


    public static final String[][] CATEGORIES = {VNEXPRESS_CATEGORIES, HAIBONGIO_CATEGORIES, DANTRI_CATEGORIES, VIETNAMNET_CATEGORIES};
    public static final String[][] LINKS = {VNEXPRESS_LINKS, HAIBONGIO_LINKS, DANTRI_LINKS,VIETNAMNET_LINKS};
    public static HashMap<Integer, List<RssItem>> MAP = new HashMap<Integer, List<RssItem>>();
  //  public static HashMap<Integer, List<RssItemVietNamNet>> MAP_VIET_NAM_NET = new HashMap<Integer, List<RssItemVietNamNet>>();

}
