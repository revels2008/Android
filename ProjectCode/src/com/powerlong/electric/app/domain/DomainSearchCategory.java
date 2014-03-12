/**
 * 宝龙电商
 * com.powerlong.electric.app.domain
 * SearchCategories.java
 * 
 * 2013-11-5-上午09:26:46
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.domain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * SearchCategories
 * 
 * @author: hegao
 * 2013-11-5-上午09:26:46
 * 
 * @version 1.0.0
 * 
 * http://plocc.powerlong.com/shopWeb/mobile/getAppItemCategorys.htm?data={%22mall%22:1}
 * {
    "code": 0,
    "data": {
        "navigationList": [
            {
                "appItemCategoryId": -1,
                "description": "休闲零食、进口食品、保健饮品",
                "id": 9,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/shipin.png",
                "lowerCategoryList": [
                    {
                        "appItemCategoryId": 110,
                        "data": {
                            "categoryId": "110",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 57,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "饮品",
                        "pid": 9
                    },
                    {
                        "appItemCategoryId": 111,
                        "data": {
                            "categoryId": "111",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 58,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "粮油米面",
                        "pid": 9
                    },
                    {
                        "appItemCategoryId": 112,
                        "data": {
                            "categoryId": "112",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 59,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "OTC药品",
                        "pid": 9
                    },
                    {
                        "appItemCategoryId": 204,
                        "data": {
                            "categoryId": "204",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 60,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "蔬果",
                        "pid": 9
                    },
                    {
                        "appItemCategoryId": 205,
                        "data": {
                            "categoryId": "205",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 61,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "肉禽",
                        "pid": 9
                    },
                    {
                        "appItemCategoryId": 103,
                        "data": {
                            "categoryId": "103",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 55,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "休闲零食",
                        "pid": 9
                    },
                    {
                        "appItemCategoryId": 108,
                        "data": {
                            "categoryId": "108",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 56,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "营养保健",
                        "pid": 9
                    }
                ],
                "name": "食品",
                "pid": 0
            },
            {
                "appItemCategoryId": -1,
                "description": "婴幼食品、婴幼用品、孕妇必备",
                "id": 10,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/muying.png",
                "lowerCategoryList": [
                    {
                        "appItemCategoryId": 113,
                        "data": {
                            "categoryId": "113",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 62,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "婴儿食品",
                        "pid": 10
                    },
                    {
                        "appItemCategoryId": 114,
                        "data": {
                            "categoryId": "114",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 63,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "婴儿用品",
                        "pid": 10
                    },
                    {
                        "appItemCategoryId": 116,
                        "data": {
                            "categoryId": "116",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 64,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "早教玩具",
                        "pid": 10
                    },
                    {
                        "appItemCategoryId": 118,
                        "data": {
                            "categoryId": "118",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 65,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "孕妇用品",
                        "pid": 10
                    }
                ],
                "name": "母婴",
                "pid": 0
            },
            {
                "appItemCategoryId": -1,
                "description": "内饰外饰、备件改装、养护套装",
                "id": 11,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/qiche.png",
                "lowerCategoryList": [
                    {
                        "appItemCategoryId": 122,
                        "data": {
                            "categoryId": "122",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 66,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "汽车用品",
                        "pid": 11
                    },
                    {
                        "appItemCategoryId": 130,
                        "data": {
                            "categoryId": "130",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 67,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "摩托车用品",
                        "pid": 11
                    }
                ],
                "name": "汽车",
                "pid": 0
            },
            {
                "appItemCategoryId": -1,
                "description": "文化用品、办公耗材、乐器玩具",
                "id": 12,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/wenyu.png",
                "lowerCategoryList": [
                    {
                        "appItemCategoryId": 76,
                        "data": {
                            "categoryId": "76",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 73,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "玩具",
                        "pid": 12
                    },
                    {
                        "appItemCategoryId": 77,
                        "data": {
                            "categoryId": "77",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 74,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "网络游戏点卡",
                        "pid": 12
                    },
                    {
                        "appItemCategoryId": 78,
                        "data": {
                            "categoryId": "78",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 75,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "动漫模型",
                        "pid": 12
                    },
                    {
                        "appItemCategoryId": 96,
                        "data": {
                            "categoryId": "96",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 76,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "宠物用品",
                        "pid": 12
                    },
                    {
                        "appItemCategoryId": 132,
                        "data": {
                            "categoryId": "132",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 68,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "书刊杂志",
                        "pid": 12
                    },
                    {
                        "appItemCategoryId": 133,
                        "data": {
                            "categoryId": "133",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 69,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "音像影视",
                        "pid": 12
                    },
                    {
                        "appItemCategoryId": 134,
                        "data": {
                            "categoryId": "134",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 70,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "乐器",
                        "pid": 12
                    },
                    {
                        "appItemCategoryId": 135,
                        "data": {
                            "categoryId": "135",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 71,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "其他",
                        "pid": 12
                    },
                    {
                        "appItemCategoryId": 137,
                        "data": {
                            "categoryId": "137",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 72,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "爱好收藏",
                        "pid": 12
                    }
                ],
                "name": "文娱",
                "pid": 0
            },
            {
                "appItemCategoryId": -1,
                "description": "宠物用品、鲜花园艺、个性定制",
                "id": 13,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/fuwu.png",
                "lowerCategoryList": [
                    {
                        "appItemCategoryId": 142,
                        "data": {
                            "categoryId": "142",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 77,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "鲜花园艺",
                        "pid": 13
                    },
                    {
                        "appItemCategoryId": 145,
                        "data": {
                            "categoryId": "145",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 78,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "个性定制",
                        "pid": 13
                    }
                ],
                "name": "服务",
                "pid": 0
            },
            {
                "appItemCategoryId": -1,
                "description": "咖啡软饮、美食汇聚、多国料理",
                "id": 14,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/canyin.png",
                "lowerCategoryList": [
                    {
                        "appItemCategoryId": 161,
                        "data": {
                            "categoryId": "161",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 89,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "火锅",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 163,
                        "data": {
                            "categoryId": "163",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 90,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "小吃快餐",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 164,
                        "data": {
                            "categoryId": "164",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 91,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "日本",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 165,
                        "data": {
                            "categoryId": "165",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 92,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "韩国料理",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 166,
                        "data": {
                            "categoryId": "166",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 93,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "东南亚菜",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 167,
                        "data": {
                            "categoryId": "167",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 94,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "西餐",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 151,
                        "data": {
                            "categoryId": "151",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 79,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "本帮江浙菜",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 168,
                        "data": {
                            "categoryId": "168",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 95,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "面包甜点",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 152,
                        "data": {
                            "categoryId": "152",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 80,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "川菜",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 169,
                        "data": {
                            "categoryId": "169",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 96,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "酒水饮料",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 153,
                        "data": {
                            "categoryId": "153",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 81,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "粤菜",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 170,
                        "data": {
                            "categoryId": "170",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 97,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "果蔬、生鲜",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 154,
                        "data": {
                            "categoryId": "154",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 82,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "湘菜",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 171,
                        "data": {
                            "categoryId": "171",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 98,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "其他",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 155,
                        "data": {
                            "categoryId": "155",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 83,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "东北菜",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 156,
                        "data": {
                            "categoryId": "156",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 84,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "贵州菜",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 157,
                        "data": {
                            "categoryId": "157",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 85,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "台湾菜",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 158,
                        "data": {
                            "categoryId": "158",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 86,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "新疆/清真",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 159,
                        "data": {
                            "categoryId": "159",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 87,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "西北菜",
                        "pid": 14
                    },
                    {
                        "appItemCategoryId": 160,
                        "data": {
                            "categoryId": "160",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 88,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "素菜",
                        "pid": 14
                    }
                ],
                "name": "餐饮",
                "pid": 0
            },
            {
                "appItemCategoryId": -1,
                "description": "家常菜、私房菜、创意菜",
                "id": 15,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/caipin.png",
                "lowerCategoryList": [
                    {
                        "appItemCategoryId": 188,
                        "data": {
                            "categoryId": "188",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 105,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "凉菜",
                        "pid": 15
                    },
                    {
                        "appItemCategoryId": 189,
                        "data": {
                            "categoryId": "189",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 106,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "甜品",
                        "pid": 15
                    },
                    {
                        "appItemCategoryId": 190,
                        "data": {
                            "categoryId": "190",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 107,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "糕点",
                        "pid": 15
                    },
                    {
                        "appItemCategoryId": 191,
                        "data": {
                            "categoryId": "191",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 108,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "小吃",
                        "pid": 15
                    },
                    {
                        "appItemCategoryId": 192,
                        "data": {
                            "categoryId": "192",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 109,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "酒水饮料",
                        "pid": 15
                    },
                    {
                        "appItemCategoryId": 206,
                        "data": {
                            "categoryId": "206",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 110,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "软饮",
                        "pid": 15
                    },
                    {
                        "appItemCategoryId": 193,
                        "data": {
                            "categoryId": "193",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 111,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "腌制",
                        "pid": 15
                    },
                    {
                        "appItemCategoryId": 194,
                        "data": {
                            "categoryId": "194",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 112,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "酱料",
                        "pid": 15
                    },
                    {
                        "appItemCategoryId": 195,
                        "data": {
                            "categoryId": "195",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 113,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "海鲜",
                        "pid": 15
                    },
                    {
                        "appItemCategoryId": 196,
                        "data": {
                            "categoryId": "196",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 114,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "水果",
                        "pid": 15
                    },
                    {
                        "appItemCategoryId": 182,
                        "data": {
                            "categoryId": "182",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 99,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "家常菜",
                        "pid": 15
                    },
                    {
                        "appItemCategoryId": 197,
                        "data": {
                            "categoryId": "197",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 115,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "其他",
                        "pid": 15
                    },
                    {
                        "appItemCategoryId": 183,
                        "data": {
                            "categoryId": "183",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 100,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "私房菜",
                        "pid": 15
                    },
                    {
                        "appItemCategoryId": 184,
                        "data": {
                            "categoryId": "184",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 101,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "创意菜",
                        "pid": 15
                    },
                    {
                        "appItemCategoryId": 185,
                        "data": {
                            "categoryId": "185",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 102,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "汤羹",
                        "pid": 15
                    },
                    {
                        "appItemCategoryId": 186,
                        "data": {
                            "categoryId": "186",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 103,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "粥",
                        "pid": 15
                    },
                    {
                        "appItemCategoryId": 187,
                        "data": {
                            "categoryId": "187",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 104,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "主食",
                        "pid": 15
                    }
                ],
                "name": "菜品",
                "pid": 0
            },
            {
                "appItemCategoryId": -1,
                "description": "娱乐教育、日用百货、摄影爱好",
                "id": 16,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/shenghuo.png",
                "lowerCategoryList": [
                    {
                        "appItemCategoryId": 203,
                        "data": {
                            "categoryId": "203",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 121,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "其他",
                        "pid": 16
                    },
                    {
                        "appItemCategoryId": 198,
                        "data": {
                            "categoryId": "198",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 116,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "娱乐",
                        "pid": 16
                    },
                    {
                        "appItemCategoryId": 199,
                        "data": {
                            "categoryId": "199",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 117,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "教育",
                        "pid": 16
                    },
                    {
                        "appItemCategoryId": 200,
                        "data": {
                            "categoryId": "200",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 118,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "运动",
                        "pid": 16
                    },
                    {
                        "appItemCategoryId": 201,
                        "data": {
                            "categoryId": "201",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 119,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "美容",
                        "pid": 16
                    },
                    {
                        "appItemCategoryId": 202,
                        "data": {
                            "categoryId": "202",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 120,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "摄影",
                        "pid": 16
                    }
                ],
                "name": "生活",
                "pid": 0
            },
            {
                "appItemCategoryId": -1,
                "description": "男女新装、婴幼童装、婚纱礼服",
                "id": 1,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/fuzhuang.png",
                "lowerCategoryList": [
                    {
                        "appItemCategoryId": 52,
                        "data": {
                            "categoryId": "52",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 9,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "运动服",
                        "pid": 1
                    },
                    {
                        "appItemCategoryId": 19,
                        "data": {
                            "categoryId": "19",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 1,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "女装",
                        "pid": 1
                    },
                    {
                        "appItemCategoryId": 20,
                        "data": {
                            "categoryId": "20",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 2,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "男装",
                        "pid": 1
                    },
                    {
                        "appItemCategoryId": 21,
                        "data": {
                            "categoryId": "21",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 3,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "童装",
                        "pid": 1
                    },
                    {
                        "appItemCategoryId": 22,
                        "data": {
                            "categoryId": "22",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 4,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "孕妇装",
                        "pid": 1
                    },
                    {
                        "appItemCategoryId": 23,
                        "data": {
                            "categoryId": "23",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 5,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "运动装",
                        "pid": 1
                    },
                    {
                        "appItemCategoryId": 24,
                        "data": {
                            "categoryId": "24",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 6,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "家居服",
                        "pid": 1
                    },
                    {
                        "appItemCategoryId": 25,
                        "data": {
                            "categoryId": "25",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 7,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "婴儿服",
                        "pid": 1
                    },
                    {
                        "appItemCategoryId": 26,
                        "data": {
                            "categoryId": "26",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 8,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "内衣",
                        "pid": 1
                    }
                ],
                "name": "服装",
                "pid": 0
            },
            {
                "appItemCategoryId": -1,
                "description": "时尚旅行箱包、商务皮鞋、钱包卡套",
                "id": 2,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/xiebao.png",
                "lowerCategoryList": [
                    {
                        "appItemCategoryId": 28,
                        "data": {
                            "categoryId": "28",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 10,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "女鞋",
                        "pid": 2
                    },
                    {
                        "appItemCategoryId": 29,
                        "data": {
                            "categoryId": "29",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 11,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "男鞋",
                        "pid": 2
                    },
                    {
                        "appItemCategoryId": 30,
                        "data": {
                            "categoryId": "30",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 12,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "运动鞋",
                        "pid": 2
                    },
                    {
                        "appItemCategoryId": 31,
                        "data": {
                            "categoryId": "31",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 13,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "女包",
                        "pid": 2
                    },
                    {
                        "appItemCategoryId": 32,
                        "data": {
                            "categoryId": "32",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 14,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "男包",
                        "pid": 2
                    },
                    {
                        "appItemCategoryId": 33,
                        "data": {
                            "categoryId": "33",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 15,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "功能箱包",
                        "pid": 2
                    }
                ],
                "name": "鞋包",
                "pid": 0
            },
            {
                "appItemCategoryId": -1,
                "description": "耳环手链、项链胸针、手镯吊坠",
                "id": 3,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/peishi.png",
                "lowerCategoryList": [
                    {
                        "appItemCategoryId": 36,
                        "data": {
                            "categoryId": "36",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 16,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "配件",
                        "pid": 3
                    },
                    {
                        "appItemCategoryId": 37,
                        "data": {
                            "categoryId": "37",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 17,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "珠宝首饰",
                        "pid": 3
                    },
                    {
                        "appItemCategoryId": 38,
                        "data": {
                            "categoryId": "38",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 18,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "手表",
                        "pid": 3
                    },
                    {
                        "appItemCategoryId": 40,
                        "data": {
                            "categoryId": "40",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 19,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "眼镜",
                        "pid": 3
                    },
                    {
                        "appItemCategoryId": 27,
                        "data": {
                            "categoryId": "27",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 20,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "其他",
                        "pid": 3
                    },
                    {
                        "appItemCategoryId": 34,
                        "data": {
                            "categoryId": "34",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 21,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "服饰配饰",
                        "pid": 3
                    },
                    {
                        "appItemCategoryId": 35,
                        "data": {
                            "categoryId": "35",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 22,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "皮带/帽子/围巾",
                        "pid": 3
                    }
                ],
                "name": "配饰",
                "pid": 0
            },
            {
                "appItemCategoryId": -1,
                "description": "护肤护发、香水香氛、美妆美体",
                "id": 4,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/meizhuang.png",
                "lowerCategoryList": [
                    {
                        "appItemCategoryId": 45,
                        "data": {
                            "categoryId": "45",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 25,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "香薰用品",
                        "pid": 4
                    },
                    {
                        "appItemCategoryId": 47,
                        "data": {
                            "categoryId": "47",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 26,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "精油芳疗",
                        "pid": 4
                    },
                    {
                        "appItemCategoryId": 49,
                        "data": {
                            "categoryId": "49",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 27,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "美妆工具",
                        "pid": 4
                    },
                    {
                        "appItemCategoryId": 50,
                        "data": {
                            "categoryId": "50",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 28,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "美发护发",
                        "pid": 4
                    },
                    {
                        "appItemCategoryId": 140,
                        "data": {
                            "categoryId": "140",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 29,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "美体",
                        "pid": 4
                    },
                    {
                        "appItemCategoryId": 141,
                        "data": {
                            "categoryId": "141",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 30,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "精油",
                        "pid": 4
                    },
                    {
                        "appItemCategoryId": 42,
                        "data": {
                            "categoryId": "42",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 23,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "美容护肤",
                        "pid": 4
                    },
                    {
                        "appItemCategoryId": 44,
                        "data": {
                            "categoryId": "44",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 24,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "彩妆香水",
                        "pid": 4
                    }
                ],
                "name": "美妆",
                "pid": 0
            },
            {
                "appItemCategoryId": -1,
                "description": "跑步健身、户外登山、垂钓狩猎",
                "id": 5,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/yundong.png",
                "lowerCategoryList": [
                    {
                        "appItemCategoryId": 53,
                        "data": {
                            "categoryId": "53",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 31,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "户外用品",
                        "pid": 5
                    },
                    {
                        "appItemCategoryId": 55,
                        "data": {
                            "categoryId": "55",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 32,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "健身用品",
                        "pid": 5
                    }
                ],
                "name": "运动",
                "pid": 0
            },
            {
                "appItemCategoryId": -1,
                "description": "平板电脑、数码相机、智能手机",
                "id": 6,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/shuma.png",
                "lowerCategoryList": [
                    {
                        "appItemCategoryId": 58,
                        "data": {
                            "categoryId": "58",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 33,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "手机通讯",
                        "pid": 6
                    },
                    {
                        "appItemCategoryId": 59,
                        "data": {
                            "categoryId": "59",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 34,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "电脑",
                        "pid": 6
                    },
                    {
                        "appItemCategoryId": 62,
                        "data": {
                            "categoryId": "62",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 35,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "电脑周边",
                        "pid": 6
                    },
                    {
                        "appItemCategoryId": 63,
                        "data": {
                            "categoryId": "63",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 36,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "相机",
                        "pid": 6
                    },
                    {
                        "appItemCategoryId": 66,
                        "data": {
                            "categoryId": "66",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 37,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "移动影音",
                        "pid": 6
                    },
                    {
                        "appItemCategoryId": 69,
                        "data": {
                            "categoryId": "69",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 38,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "办公设备",
                        "pid": 6
                    },
                    {
                        "appItemCategoryId": 70,
                        "data": {
                            "categoryId": "70",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 39,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "数码配件",
                        "pid": 6
                    },
                    {
                        "appItemCategoryId": 71,
                        "data": {
                            "categoryId": "71",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 40,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "电玩",
                        "pid": 6
                    }
                ],
                "name": "数码",
                "pid": 0
            },
            {
                "appItemCategoryId": -1,
                "description": "厨房电器、生活电器、个人护理",
                "id": 7,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/jiadian.png",
                "lowerCategoryList": [
                    {
                        "appItemCategoryId": 79,
                        "data": {
                            "categoryId": "79",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 41,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "家电配件",
                        "pid": 7
                    },
                    {
                        "appItemCategoryId": 80,
                        "data": {
                            "categoryId": "80",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 42,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "生活电器",
                        "pid": 7
                    },
                    {
                        "appItemCategoryId": 81,
                        "data": {
                            "categoryId": "81",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 43,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "厨房电器",
                        "pid": 7
                    },
                    {
                        "appItemCategoryId": 82,
                        "data": {
                            "categoryId": "82",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 44,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "影音电器",
                        "pid": 7
                    },
                    {
                        "appItemCategoryId": 83,
                        "data": {
                            "categoryId": "83",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 45,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "个人护理",
                        "pid": 7
                    }
                ],
                "name": "家电",
                "pid": 0
            },
            {
                "appItemCategoryId": -1,
                "description": "住宅家具、床上用品、家居饰品",
                "id": 8,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/jujia.png",
                "lowerCategoryList": [
                    {
                        "appItemCategoryId": 84,
                        "data": {
                            "categoryId": "84",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 46,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "家具",
                        "pid": 8
                    },
                    {
                        "appItemCategoryId": 86,
                        "data": {
                            "categoryId": "86",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 47,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "家装设计",
                        "pid": 8
                    },
                    {
                        "appItemCategoryId": 87,
                        "data": {
                            "categoryId": "87",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 48,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "五金电工",
                        "pid": 8
                    },
                    {
                        "appItemCategoryId": 91,
                        "data": {
                            "categoryId": "91",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 49,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "居家日用",
                        "pid": 8
                    },
                    {
                        "appItemCategoryId": 92,
                        "data": {
                            "categoryId": "92",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 50,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "收纳整理",
                        "pid": 8
                    },
                    {
                        "appItemCategoryId": 94,
                        "data": {
                            "categoryId": "94",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 51,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "餐饮用具",
                        "pid": 8
                    },
                    {
                        "appItemCategoryId": 95,
                        "data": {
                            "categoryId": "95",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 52,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "洗护清洁",
                        "pid": 8
                    },
                    {
                        "appItemCategoryId": 99,
                        "data": {
                            "categoryId": "99",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 53,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "家纺布艺",
                        "pid": 8
                    },
                    {
                        "appItemCategoryId": 147,
                        "data": {
                            "categoryId": "147",
                            "classification": "item",
                            "mall": 1,
                            "orderBy": 0,
                            "page": 1
                        },
                        "description": " ",
                        "id": 54,
                        "level": 2,
                        "logo": " ",
                        "mallId": 1,
                        "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
                        "name": "成人用品",
                        "pid": 8
                    }
                ],
                "name": "居家",
                "pid": 0
            }
        ],
        "version": 1
    },
    "msg": ""
}
 * {
                "appItemCategoryId": -1,
                "description": "休闲零食、进口食品、保健饮品",
                "id": 9,
                "level": 1,
                "logo": "http://www.powerlongmall.com.cn/mobile/search/shipin.png",
                
                "name": "食品",
                "pid": 0
            },
 */
public class DomainSearchCategory {
	public static List<DomainSearchCategory> convertJsonToSearCategory(Context context,String json){
		List<DomainSearchCategory> list = new ArrayList<DomainSearchCategory>();
		try {
			JSONArray jsArr = new JSONArray(json);
			for(int i = 0;i<jsArr.length();i++){
				JSONObject jsObj = jsArr.getJSONObject(i);
				DomainSearchCategory searchCategory = new DomainSearchCategory();
				searchCategory.setAppItemCategoryId(jsObj.optString("appItemCategoryId", ""));
				searchCategory.setDescription(jsObj.optString("description", ""));
				searchCategory.setId(jsObj.optString("id", ""));
				searchCategory.setLevel(jsObj.optString("level", ""));
				searchCategory.setLogo(jsObj.optString("logo", ""));
				searchCategory.setName(jsObj.optString("name", ""));
				searchCategory.setPid(jsObj.optString("pid", ""));
				searchCategory.setLowerCategoryList(jsObj.optString("lowerCategoryList", ""));
				list.add(searchCategory);
			}
		} catch (Exception e) {
			Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		}
		return list;
	}
	
	
	private String appItemCategoryId;
	private String description;
	private String id;
	private String level;
	private String logo;
	private String name;
	private String pid;
	private String lowerCategoryList;
	
	public String getLowerCategoryList() {
		return lowerCategoryList;
	}
	public void setLowerCategoryList(String lowerCategoryList) {
		this.lowerCategoryList = lowerCategoryList;
	}
	public String getAppItemCategoryId() {
		return appItemCategoryId;
	}
	public void setAppItemCategoryId(String appItemCategoryId) {
		this.appItemCategoryId = appItemCategoryId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
}
