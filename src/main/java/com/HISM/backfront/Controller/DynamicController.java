package com.HISM.backfront.Controller;

import com.HISM.backfront.Config.WebAppConfig;
import com.HISM.backfront.Result.MyResult;
import com.HISM.backfront.Service.*;
import com.HISM.backfront.domain.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@RestController
//必填
@Api(tags = "动态管理接口")
@RequestMapping("/moment")
public class DynamicController {

    @Resource
    DynamicSerive dynamicSerive;
    @Resource
    UserService userService;
    @Resource
    GeneralService generalService;
    @Resource
    ThumbSerive thumbSerive;
    @Resource
    CommentSerive commentSerive;
    @Resource
    TipOffDynamicSerive tipOffDynamicSerive;
    @Resource
    FollowerSerive followerSerive;
    @Resource
    WebAppConfig webAppConfig;

    @PostMapping("/getMoments")
    //必填
    @ApiOperation("获取动态")
    public MyResult getMoments(@RequestParam String userId, @RequestParam int type, @RequestParam int lastMomentId, @RequestParam int length) {
        MyResult myResult = new MyResult();
        if ("".equals(userId)) {
            myResult.changeStatus(false);
            myResult.add("message", "源用户id，目标用户id不能为空");
            return myResult;
        }
        //查看当前发起用户请求用户是否存在
        List<User> userList = userService.selectUserbyId(userId);
        if (userList == null) {
            myResult.changeStatus(false);
            myResult.add("message", "没有源用户");
        } else if (userList.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "源用户信息多于一个");
        } else {
            //关注
            if (type == 1) {
                //如果是第一次拉取
                List<User> follwers = userService.getSubscriberByUserId(userId);
                //如果该用户没有关注
                if (follwers.size() == 0) {
                    myResult.changeStatus(true);
                    myResult.add("message", "");
                } else {
                    //将取出的follwers的动态根据时间插在一起
                    List<Dynamic> tmp = new ArrayList<Dynamic>();
                    for (User user : follwers) {
                        List<Dynamic> d = dynamicSerive.selectDynamicByUserIdAndState(user.getUserId(), 2);
                        tmp.addAll(d);
                    }
                    List<Dynamic> d = dynamicSerive.selectDynamicByUserIdAnd2State(userId, 1, 2);
                    tmp.addAll(d);
                    tmp.sort(new Comparator<Dynamic>() {
                        @Override
                        public int compare(Dynamic o1, Dynamic o2) {
                            return o2.getDynamicTime().compareTo(o1.getDynamicTime());
                        }
                    });
                    //如果从第一个拉取
                    if (lastMomentId == -1) {
                        if (tmp.size() >= length) {
                            //数据库中的数量大于所取长度
                            myResult.changeStatus(true);
                            List<Map<String, Object>> tmp1 = new ArrayList<>();
                            for (int i = 0; i < length; ++i) {
                                Map<String, Object> map = new HashMap<>(4);
                                map.put("momentId", tmp.get(i).getDynamicId());
                                map.put("userID", tmp.get(i).getUserId());
                                List<User> t = userService.selectUserbyId(tmp.get(i).getUserId());
                                map.put("userName", t.get(0).getUserName());
                                map.put("userAvatar", t.get(0).getAvatarURL());
                                map.put("text", tmp.get(i).getText());
                                map.put("appendixType", tmp.get(i).getDynamicType());
                                String dynamicType = tmp.get(i).getDynamicType();
                                if (dynamicType.equals("1")) {
                                    int imageLength = tmp.get(i).getDynamicContent().split(";").length;
                                    if (imageLength == 0) {
                                        myResult.changeStatus(false);
                                        myResult.add("message", "动态中不包含图片");
                                        return myResult;
                                    } else {
                                        map.put("photos", tmp.get(i).getDynamicContent());
                                    }
                                } else if (dynamicType.equals("2")) {
                                    map.put("video", tmp.get(i).getDynamicContent());
                                } else if (dynamicType.equals("3") || dynamicType.equals("4")) {
                                    map.put("momentId", tmp.get(i).getDynamicId());
                                } else if (dynamicType.equals("0")) {
                                } else {
                                    myResult.changeStatus(false);
                                    myResult.add("message", "动态类型码错误");
                                    return myResult;
                                }
                                map.put("likedNum", tmp.get(i).getThumbNum());
                                map.put("isLiked", thumbSerive.isThumb(userId, tmp.get(i).getDynamicId()));
                                map.put("isDel", tmp.get(i).getDynamicState() == 3);
                                map.put("tag", tmp.get(i).getDynamicIndex());
                                map.put("commentNum", tmp.get(i).getCommentNum());
                                map.put("time", tmp.get(i).getDynamicTime());
                                tmp1.add(map);
                            }
                            myResult.add("message", tmp1);
                        } else {
                            myResult.changeStatus(true);
                            List<Map<String, Object>> tmp1 = new ArrayList<>();
                            for (Dynamic dynamic : tmp) {
                                Map<String, Object> map = new HashMap<>(4);
                                map.put("momentId", dynamic.getDynamicId());
                                map.put("userID", dynamic.getUserId());
                                List<User> t = userService.selectUserbyId(dynamic.getUserId());
                                map.put("userName", t.get(0).getUserName());
                                map.put("userAvatar", t.get(0).getAvatarURL());
                                map.put("text", dynamic.getText());
                                map.put("appendixType", dynamic.getDynamicType());
                                String dynamicType = dynamic.getDynamicType();
                                if (dynamicType.equals("1")) {
                                    int imageLength = dynamic.getDynamicContent().split(";").length;
                                    if (imageLength == 0) {
                                        myResult.changeStatus(false);
                                        myResult.add("message", "动态中不包含图片");
                                        return myResult;
                                    } else {
                                        map.put("photos", dynamic.getDynamicContent());
                                    }
                                } else if (dynamicType.equals("2")) {
                                    map.put("video", dynamic.getDynamicContent());
                                } else if (dynamicType.equals("3") || dynamicType.equals("4")) {
                                    map.put("momentId", dynamic.getDynamicId());
                                } else if (dynamicType.equals("0")) {
                                } else {
                                    myResult.changeStatus(false);
                                    myResult.add("message", "动态类型码错误");
                                    return myResult;
                                }
                                map.put("likedNum", dynamic.getThumbNum());
                                map.put("isLiked", thumbSerive.isThumb(userId, dynamic.getDynamicId()));
                                map.put("isDel", dynamic.getDynamicState() == 3);
                                map.put("tag", dynamic.getDynamicIndex());
                                map.put("commentNum", dynamic.getCommentNum());
                                map.put("time", dynamic.getDynamicTime());
                                tmp1.add(map);
                            }
                            myResult.add("message", tmp1);
                        }
                    }
                    //从第某个id拉取
                    else {
                        Dynamic dynamic = dynamicSerive.selectDynamicByDynamicId(lastMomentId);
                        if (dynamic == null) {
                            myResult.changeStatus(false);
                            myResult.add("message", "该id对应动态不存在");
                            return myResult;
                        }
                        int index = tmp.indexOf(dynamic);
                        if ((tmp.size() - (index + 1)) >= length) {
                            //数据库中的数量大于所取长度
                            myResult.changeStatus(true);
                            List<Map<String, Object>> tmp1 = new ArrayList<>();
                            for (int i = index + 1; i < length; ++i) {
                                Map<String, Object> map = new HashMap<>(4);
                                map.put("momentId", tmp.get(i).getDynamicId());
                                map.put("userID", tmp.get(i).getUserId());
                                List<User> t = userService.selectUserbyId(tmp.get(i).getUserId());
                                map.put("userName", t.get(0).getUserName());
                                map.put("userAvatar", t.get(0).getAvatarURL());
                                map.put("text", tmp.get(i).getText());
                                map.put("appendixType", tmp.get(i).getDynamicType());
                                String dynamicType = tmp.get(i).getDynamicType();
                                if (dynamicType.equals("1")) {
                                    int imageLength = tmp.get(i).getDynamicContent().split(";").length;
                                    if (imageLength == 0) {
                                        myResult.changeStatus(false);
                                        myResult.add("message", "动态中不包含图片");
                                        return myResult;
                                    } else {
                                        map.put("photos", tmp.get(i).getDynamicContent());
                                    }
                                } else if (dynamicType.equals("2")) {
                                    map.put("video", tmp.get(i).getDynamicContent());
                                } else if (dynamicType.equals("3") || dynamicType.equals("4")) {
                                    map.put("momentId", tmp.get(i).getDynamicId());
                                } else if (dynamicType.equals("0")) {
                                } else {
                                    myResult.changeStatus(false);
                                    myResult.add("message", "动态类型码错误");
                                    return myResult;
                                }
                                map.put("likedNum", tmp.get(i).getThumbNum());
                                map.put("isLiked", thumbSerive.isThumb(userId, tmp.get(i).getDynamicId()));
                                map.put("isDel", tmp.get(i).getDynamicState() == 3);
                                map.put("tag", tmp.get(i).getDynamicIndex());
                                map.put("commentNum", tmp.get(i).getCommentNum());
                                map.put("time", tmp.get(i).getDynamicTime());
                                tmp1.add(map);
                            }
                            myResult.add("message", tmp1);
                        } else {
                            //数据库中的数量大于所取长度
                            myResult.changeStatus(true);
                            List<Map<String, Object>> tmp1 = new ArrayList<>();
                            for (int i = index + 1; i < tmp.size(); ++i) {
                                Map<String, Object> map = new HashMap<>(4);
                                map.put("momentId", tmp.get(i).getDynamicId());
                                map.put("userID", tmp.get(i).getUserId());
                                List<User> t = userService.selectUserbyId(tmp.get(i).getUserId());
                                map.put("userName", t.get(0).getUserName());
                                map.put("userAvatar", t.get(0).getAvatarURL());
                                map.put("text", tmp.get(i).getText());
                                map.put("appendixType", tmp.get(i).getDynamicType());
                                String dynamicType = tmp.get(i).getDynamicType();
                                if (dynamicType.equals("1")) {
                                    int imageLength = tmp.get(i).getDynamicContent().split(";").length;
                                    if (imageLength == 0) {
                                        myResult.changeStatus(false);
                                        myResult.add("message", "动态中不包含图片");
                                        return myResult;
                                    } else {
                                        map.put("photos", tmp.get(i).getDynamicContent());
                                    }
                                } else if (dynamicType.equals("2")) {
                                    map.put("video", tmp.get(i).getDynamicContent());
                                } else if (dynamicType.equals("3") || dynamicType.equals("4")) {
                                    map.put("momentId", tmp.get(i).getDynamicId());
                                } else if (dynamicType.equals("0")) {
                                } else {
                                    myResult.changeStatus(false);
                                    myResult.add("message", "动态类型码错误");
                                    return myResult;
                                }
                                map.put("likedNum", tmp.get(i).getThumbNum());
                                map.put("isLiked", thumbSerive.isThumb(userId, tmp.get(i).getDynamicId()));
                                map.put("isDel", tmp.get(i).getDynamicState() == 3);
                                map.put("tag", tmp.get(i).getDynamicIndex());
                                map.put("commentNum", tmp.get(i).getCommentNum());
                                map.put("time", tmp.get(i).getDynamicTime());
                                tmp1.add(map);
                            }
                            myResult.add("message", tmp1);
                        }
                    }
                }
            } else if (type == 0) {
                //广场
                //如果从第一个拉取
                List<Dynamic> tmp = dynamicSerive.selectDynamicByState(2);
                if (lastMomentId == -1) {
                    if (tmp.size() >= length) {
                        //数据库中的数量大于所取长度
                        myResult.changeStatus(true);
                        List<Map<String, Object>> tmp1 = new ArrayList<>();
                        for (int i = 0; i < length; ++i) {
                            Map<String, Object> map = new HashMap<>(4);
                            map.put("momentId", tmp.get(i).getDynamicId());
                            map.put("userID", tmp.get(i).getUserId());
                            List<User> t = userService.selectUserbyId(tmp.get(i).getUserId());
                            map.put("userName", t.get(0).getUserName());
                            map.put("userAvatar", t.get(0).getAvatarURL());
                            map.put("text", tmp.get(i).getText());
                            map.put("appendixType", tmp.get(i).getDynamicType());
                            String dynamicType = tmp.get(i).getDynamicType();
                            if (dynamicType.equals("1")) {
                                int imageLength = tmp.get(i).getDynamicContent().split(";").length;
                                if (imageLength == 0) {
                                    myResult.changeStatus(false);
                                    myResult.add("message", "动态中不包含图片");
                                    return myResult;
                                } else {
                                    map.put("photos", tmp.get(i).getDynamicContent());
                                }
                            } else if (dynamicType.equals("2")) {
                                map.put("video", tmp.get(i).getDynamicContent());
                            } else if (dynamicType.equals("3") || dynamicType.equals("4")) {
                                map.put("momentId", tmp.get(i).getDynamicId());
                            } else if (dynamicType.equals("0")) {
                            } else {
                                myResult.changeStatus(false);
                                myResult.add("message", "动态类型码错误");
                                return myResult;
                            }
                            map.put("likedNum", tmp.get(i).getThumbNum());
                            map.put("isLiked", thumbSerive.isThumb(userId, tmp.get(i).getDynamicId()));
                            map.put("isDel", tmp.get(i).getDynamicState() == 3);
                            map.put("tag", tmp.get(i).getDynamicIndex());
                            map.put("commentNum", tmp.get(i).getCommentNum());
                            map.put("time", tmp.get(i).getDynamicTime());
                            tmp1.add(map);
                        }
                        myResult.add("message", tmp1);
                    } else {
                        myResult.changeStatus(true);
                        List<Map<String, Object>> tmp1 = new ArrayList<>();
                        for (Dynamic dynamic : tmp) {
                            Map<String, Object> map = new HashMap<>(4);
                            map.put("momentId", dynamic.getDynamicId());
                            map.put("userID", dynamic.getUserId());
                            List<User> t = userService.selectUserbyId(dynamic.getUserId());
                            map.put("userName", t.get(0).getUserName());
                            map.put("userAvatar", t.get(0).getAvatarURL());
                            map.put("text", dynamic.getText());
                            map.put("appendixType", dynamic.getDynamicType());
                            String dynamicType = dynamic.getDynamicType();
                            if (dynamicType.equals("1")) {
                                int imageLength = dynamic.getDynamicContent().split(";").length;
                                if (imageLength == 0) {
                                    myResult.changeStatus(false);
                                    myResult.add("message", "动态中不包含图片");
                                    return myResult;
                                } else {
                                    map.put("photos", dynamic.getDynamicContent());
                                }
                            } else if (dynamicType.equals("2")) {
                                map.put("video", dynamic.getDynamicContent());
                            } else if (dynamicType.equals("3") || dynamicType.equals("4")) {
                                map.put("momentId", dynamic.getDynamicId());
                            } else if (dynamicType.equals("0")) {
                            } else {
                                myResult.changeStatus(false);
                                myResult.add("message", "动态类型码错误");
                                return myResult;
                            }
                            map.put("likedNum", dynamic.getThumbNum());
                            map.put("isLiked", thumbSerive.isThumb(userId, dynamic.getDynamicId()));
                            map.put("isDel", dynamic.getDynamicState() == 3);
                            map.put("tag", dynamic.getDynamicIndex());
                            map.put("commentNum", dynamic.getCommentNum());
                            map.put("time", dynamic.getDynamicTime());
                            tmp1.add(map);
                        }
                        myResult.add("message", tmp1);
                    }
                }
                //从第某个id拉取
                else {
                    List<Dynamic> dynamic = dynamicSerive.selectDynamicByDynamicIdLimitNUM(lastMomentId, length);
                    //数据库中的数量大于所取长度
                    myResult.changeStatus(true);
                    List<Map<String, Object>> tmp1 = new ArrayList<>();
                    for (Dynamic d : dynamic) {
                        Map<String, Object> map = new HashMap<>(4);
                        map.put("momentId", d.getDynamicId());
                        map.put("userID", d.getUserId());
                        List<User> t = userService.selectUserbyId(d.getUserId());
                        map.put("userName", t.get(0).getUserName());
                        map.put("userAvatar", t.get(0).getAvatarURL());
                        map.put("text", d.getText());
                        map.put("appendixType", d.getDynamicType());
                        String dynamicType = d.getDynamicType();
                        if (dynamicType.equals("1")) {
                            int imageLength = d.getDynamicContent().split(";").length;
                            if (imageLength == 0) {
                                myResult.changeStatus(false);
                                myResult.add("message", "动态中不包含图片");
                                return myResult;
                            } else {
                                map.put("photos", d.getDynamicContent());
                            }
                        } else if (dynamicType.equals("2")) {
                            map.put("video", d.getDynamicContent());
                        } else if (dynamicType.equals("3") || dynamicType.equals("4")) {
                            map.put("momentId", d.getDynamicId());
                        } else if (dynamicType.equals("0")) {
                        } else {
                            myResult.changeStatus(false);
                            myResult.add("message", "动态类型码错误");
                            return myResult;
                        }
                        map.put("likedNum", d.getThumbNum());
                        map.put("isLiked", thumbSerive.isThumb(userId, d.getDynamicId()));
                        map.put("isDel", d.getDynamicState() == 3);
                        map.put("tag", d.getDynamicIndex());
                        map.put("commentNum", d.getCommentNum());
                        map.put("time", d.getDynamicTime());
                        tmp1.add(map);
                    }
                    myResult.add("message", tmp1);
                }

            }
        }
        return myResult;
    }

    @PostMapping("/getMomentByID")
    //必填
    @ApiOperation("获取单个动态")
    public MyResult getMomentByID(@RequestParam String userId, @RequestParam int momentId) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(momentId)) {
            myResult.changeStatus(false);
            myResult.add("message", "源用户id，目标用户id不能为空");
            return myResult;
        }
        //查看当前发起用户请求用户是否存在
        List<User> user = userService.selectUserbyId(userId);
        if (user == null) {
            myResult.changeStatus(false);
            myResult.add("message", "没有源用户");
        } else if (user.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "源用户信息多于一个");
        } else {
            //查看当前被请求的动态是否存在
            Dynamic dynamic = dynamicSerive.selectDynamicByDynamicId(momentId);
            if (dynamic == null) {
                myResult.changeStatus(false);
                myResult.add("message", "没有该动态");
            } else {
                //添加返回值
                Map<String, Object> map = new HashMap<>(4);
                map.put("momentId", momentId);
                map.put("userID", user.get(0).getUserId());
                map.put("userName", user.get(0).getUserName());
                map.put("userAvatar", user.get(0).getAvatarURL());
                map.put("time", dynamic.getDynamicTime());
                map.put("text", dynamic.getText());
                map.put("likedNum", dynamic.getThumbNum());
                map.put("commentNum", dynamic.getCommentNum());
                map.put("isLiked", thumbSerive.isThumb(userId, dynamic.getDynamicId()));
                map.put("isDel", dynamic.getDynamicState() == 3);
                map.put("tag", dynamic.getDynamicType());
                String dynamicType = dynamic.getDynamicType();
                map.put("dynamicType", dynamicType);
                map.put("appendixType", dynamicType);
                myResult.changeStatus(true);
                //如果当前动态为照片+文字
                if (dynamicType.equals("1")) {
                    int length = dynamic.getDynamicContent().split(";").length;
                    if (length == 0) {
                        myResult.changeStatus(false);
                        myResult.add("message", "动态中不包含图片");
                        return myResult;
                    } else {
                        map.put("photos", dynamic.getDynamicContent());
                    }
                } else if (dynamicType.equals("2")) {
                    //如果当前动态为视频
                    map.put("video", dynamic.getDynamicContent());
                } else if (dynamicType.equals("3") || dynamicType.equals("4")) {
                    //如果为程序段
                    map.put("momentId", dynamic.getDynamicId());
                } else if (dynamicType.equals("0")) {
                } else {
                    myResult.changeStatus(false);
                    myResult.add("message", "动态类型码错误");
                    return myResult;
                }
                myResult.add("message", map);
            }
        }
        return myResult;
    }

    @PostMapping("/likeMoment")
    //必填
    @ApiOperation("点赞/取消点赞")
    public MyResult likeMoment(@RequestParam String userId, @RequestParam int dynamicId) {
        MyResult myResult = new MyResult();
        if ("".equals(userId)) {
            myResult.changeStatus(false);
            myResult.add("message", "用户id不能为空");
            return myResult;
        }
        List<User> user = userService.selectUserbyId(userId);
        if (user == null) {
            myResult.changeStatus(false);
            myResult.add("message", "不存在该用户");
        } else if (user.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "存在多个该用户信息");
        } else {
            Dynamic dynamic = dynamicSerive.selectDynamicByDynamicId(dynamicId);
            if (dynamic == null) {
                myResult.changeStatus(false);
                myResult.add("message", "不存在该条动态");
            } else {
                Map<String, Object> map = new HashMap<>(2);
                if (thumbSerive.isThumb(userId, dynamicId)) {
                    //如果已经点赞那么取消
                    thumbSerive.deleteThumb(dynamicId, userId);
                    int likedNum = dynamic.getThumbNum() - 1;
                    myResult.changeStatus(true);
                    map.put("isLiked", "已取消点赞");
                    map.put("likedNum", likedNum);
                } else {
                    //如果没有点赞就去点赞
                    thumbSerive.insertThumb(new Thumb(dynamicId, userId));
                    int likedNum = dynamic.getThumbNum() + 1;
                    myResult.changeStatus(true);
                    map.put("isLiked", "点赞成功");
                    map.put("likedNum", likedNum);
                }
                myResult.add("message", map);
            }
        }
        return myResult;
    }

    @PostMapping("/commentMoment")
    //必填
    @ApiOperation("发送评论")
    public MyResult commentMoment(@RequestParam String userId, @RequestParam int dynamicId, @RequestParam String commentText) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(commentText)) {
            myResult.changeStatus(false);
            myResult.add("message", "用户id或评论不能为空");
            return myResult;
        }
        List<User> user = userService.selectUserbyId(userId);
        if (user == null) {
            myResult.changeStatus(false);
            myResult.add("message", "不存在该用户");
        } else if (user.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "存在多个该用户信息");
        } else {
            Dynamic dynamic = dynamicSerive.selectDynamicByDynamicId(dynamicId);
            if (dynamic == null) {
                myResult.changeStatus(false);
                myResult.add("message", "不存在该条动态");
            } else {
                Comment comment = new Comment();
                comment.setCommentContent(commentText);
                Date date = new Date(System.currentTimeMillis());
                Timestamp timeStamp = new Timestamp(date.getTime());
                comment.setCommentTime(timeStamp);
                comment.setDynamicId(dynamicId);
                comment.setUserId(userId);
                commentSerive.insertComment(comment);
                myResult.changeStatus(true);
                myResult.add("message", "评论成功");
            }
        }
        return myResult;
    }

    @PostMapping("/getComment")
    @ApiOperation("获取评论")
    public MyResult getComment(@RequestParam String userId, @RequestParam int dynamicId) {
        MyResult myResult = new MyResult();
        if ("".equals(userId)) {
            myResult.changeStatus(false);
            myResult.add("message", "用户id不能为空");
            return myResult;
        }
        List<User> user = userService.selectUserbyId(userId);
        if (user == null) {
            myResult.changeStatus(false);
            myResult.add("message", "不存在该用户");
        } else if (user.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "存在多个该用户信息");
        } else {
            Dynamic dynamic = dynamicSerive.selectDynamicByDynamicId(dynamicId);
            if (dynamic == null) {
                myResult.changeStatus(true);
                myResult.add("message", "该条动态不存在");
            } else {
                List<Comment> commentList = commentSerive.selectCommentById(dynamicId);
                List<Map<String, Object>> tmp = new ArrayList<>();
                for (int i = 0; i < commentList.size(); i++) {
                    List<User> user_t = userService.selectUserbyId(commentList.get(i).getUserId());
                    if (user_t == null) {
                        myResult.changeStatus(true);
                        myResult.add("message", "该" + commentList.get(i).getUserId() + "用户不存在");
                    }
                    Map<String, Object> map = new HashMap<>(6);
                    map.put("commentId", commentList.get(i).getCommentId());
                    map.put("userId", user_t.get(0).getUserId());
                    map.put("userName", user_t.get(0).getUserName());
                    map.put("userAvatar", user_t.get(0).getAvatarURL());
                    map.put("time", commentList.get(i).getCommentTime());
                    map.put("text", commentList.get(i).getCommentContent());
                    tmp.add(map);
                }
                myResult.add("message", tmp);
            }
        }
        return myResult;
    }

    @PostMapping("/getLikedMomentUsers")
    //必填
    @ApiOperation("获取点赞用户列表")
    public MyResult getLikedMomentUsers(@RequestParam String userId, @RequestParam int dynamicId) {
        MyResult myResult = new MyResult();
        List<User> userList = userService.selectUserbyId(userId);
        if (userList == null) {
            myResult.changeStatus(false);
            myResult.add("message", "不存在该用户");
        } else if (userList.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "该用户信息冗余");
        } else {

            Dynamic d = dynamicSerive.selectDynamicByDynamicId(dynamicId);
            if (d == null) {
                myResult.changeStatus(false);
                myResult.add("message", "没有该动态");
            }
            List<Thumb> thumbs = thumbSerive.selectThumbInfoByDynamicId(dynamicId);
            List<Map<String, Object>> tmp = new ArrayList<>();
            for (Thumb t : thumbs) {
                Map<String, Object> map = new HashMap<>(4);
                List<User> user = userService.selectUserbyId(t.getUserId());
                if (user == null) {
                    myResult.changeStatus(false);
                    myResult.add("message", "不存在该用户");
                    return myResult;
                } else if (user.size() > 1) {
                    myResult.changeStatus(false);
                    myResult.add("message", "该用户信息冗余");
                    return myResult;
                } else {
                    map.put("userID", user.get(0).getUserId());
                    map.put("userName", user.get(0).getUserName());
                    map.put("userAvatar", user.get(0).getAvatarURL());
                    tmp.add(map);
                }

            }
            myResult.changeStatus(true);
            myResult.add("message", tmp);
        }

        return myResult;
    }

    @PostMapping("/createMomentOnlyText")
    //必填
    @ApiOperation("用户上传文本")
    public MyResult createMomentOnlyText(@RequestParam String userId, @RequestParam String text, @RequestParam String tag) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(text)) {
            myResult.changeStatus(false);
            myResult.add("message", "用户id或text为空或tag为空");
            return myResult;
        }
        List<User> users = userService.selectUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("message", "无该用户");
        } else if (users.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "用户id大于1个");
        } else {
            Dynamic dynamic = new Dynamic();
            //可见性
            dynamic.setDynamicState(2);
            //设置userid
            dynamic.setUserId(userId);
            //设置内容
            dynamic.setText(text);
            //设置动态类型
            dynamic.setDynamicType("0");
            //设置时间
            Date date = new Date(System.currentTimeMillis());
            Timestamp timeStamp = new Timestamp(date.getTime());
            dynamic.setDynamicTime(timeStamp);
            //设置标签
            dynamic.setDynamicIndex(tag);
            dynamicSerive.insertDynamic(dynamic);
            myResult.changeStatus(true);
            myResult.add("message", "");
        }
        return myResult;
    }


    @PostMapping("/createMomentWithPhotos")
    //必填
    @ApiOperation("用户上传图片+文本")
    public MyResult createMomentWithPhotos(@RequestParam String userId, @RequestParam("editormd-image-file") List<MultipartFile> multipartFile, @RequestParam String text, @RequestParam String tag) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(tag) || multipartFile == null) {
            myResult.changeStatus(false);
            myResult.add("message", "userId或tag为空");
            return myResult;
        }
        List<User> users = userService.selectUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("message", "无该用户信息");
        } else if (users.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "用户信息");
        } else {
            Dynamic dynamic = new Dynamic();
            dynamic.setText(text);
            dynamic.setDynamicState(2);
            dynamic.setDynamicType("1");
            dynamic.setDynamicIndex(tag);
            String ttt = "";
            Date date = new Date(System.currentTimeMillis());
            Timestamp timeStamp = new Timestamp(date.getTime());
            for (int i = 0; i < multipartFile.size(); ++i) {
                String name = multipartFile.get(i).getOriginalFilename();
                assert name != null;
                String[] s = name.split("\\.");
                String root_fileName = i + "-" + timeStamp + "." + s[s.length - 1];
                //获取地址
                String filePath = webAppConfig.location + "/";
                filePath += (userId + "/" + "Dynamic" + "/" + timeStamp);
                String file_name = null;
                try {
                    Map<String, String> t = new HashMap<String, String>();
                    t.put("path", filePath);
                    t.put("token", "123");
                    t.put("fileName", root_fileName);
                    file_name = generalService.saveImg(multipartFile.get(i), t);
                    if (file_name == null) {
                        myResult.changeStatus(false);
                        myResult.add("message", "文件存储失败");
                        return myResult;
                    }
                    ttt += (file_name + ";");
                } catch (IOException e) {
                    myResult.changeStatus(false);
                    myResult.add("message", "test");
                    return myResult;
                }
            }
            dynamic.setDynamicContent(ttt);
            dynamic.setDynamicTime(timeStamp);
            dynamic.setUserId(userId);
            dynamicSerive.insertDynamic(dynamic);
            myResult.changeStatus(true);
            myResult.add("message", "");
        }
        return myResult;
    }

    @PostMapping("/createMomentWithVideo")
    //必填
    @ApiOperation("用户上传视频")
    public MyResult createMomentWithVideo(@RequestParam String userId, @RequestParam("editormd-image-file") MultipartFile multipartFile, @RequestParam String text, @RequestParam String tag) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(text) || "".equals(tag) || multipartFile == null) {
            myResult.changeStatus(false);
            myResult.add("message", "userId或text或tag为空");
            return myResult;
        }
        List<User> users = userService.selectUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("message", "无该用户信息");
        } else if (users.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "用户信息");
        } else {
            Dynamic dynamic = new Dynamic();
            dynamic.setText(text);
            dynamic.setDynamicState(2);
            dynamic.setDynamicType("2");
            dynamic.setDynamicIndex(tag);
            Date date = new Date(System.currentTimeMillis());
            Timestamp timeStamp = new Timestamp(date.getTime());
            String name = multipartFile.getOriginalFilename();
            assert name != null;
            String[] s = name.split("\\.");
            String root_fileName = timeStamp + "." + s[s.length - 1];
            //获取地址
            String filePath = webAppConfig.location + "/";
            filePath += (userId + "/" + "Dynamic" + "/" + timeStamp);
            String file_name = null;
            try {
                Map<String, String> t = new HashMap<String, String>();
                t.put("path", filePath);
                t.put("token", "123");
                t.put("fileName", root_fileName);
                file_name = generalService.saveImg(multipartFile, t);
                if (file_name == null) {
                    myResult.changeStatus(false);
                    myResult.add("message", "文件存储失败");
                    return myResult;
                }
                dynamic.setDynamicContent(file_name);
            } catch (IOException e) {
                myResult.changeStatus(false);
                myResult.add("message", "test");
                return myResult;
            }
            dynamic.setDynamicTime(timeStamp);
            dynamic.setUserId(userId);
            dynamicSerive.insertDynamic(dynamic);
            myResult.changeStatus(true);
            myResult.add("message", "");
        }
        return myResult;
    }

    @PostMapping("delComment")
    @ApiOperation("删除评论")
    public MyResult delComment(@RequestParam String userId, @RequestParam int commentId) {
        MyResult myResult = new MyResult();
        if ("".equals(userId)) {
            myResult.changeStatus(false);
            myResult.add("message", "用户id不能为空");
            return myResult;
        }
        List<User> user = userService.selectUserbyId(userId);
        if (user == null) {
            myResult.changeStatus(false);
            myResult.add("message", "该用户不存在");
        } else if (user.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "存在多个该用户信息");
        } else {
            int dynamicId = commentSerive.getDynamicId(commentId);
            Dynamic dynamic = dynamicSerive.selectDynamicByDynamicId(dynamicId);
            if (dynamic == null) {
                myResult.changeStatus(false);
                myResult.add("message", "该评论所属的动态不存在");
            } else {
                if (!commentSerive.isComment(userId, dynamicId)) {
                    myResult.changeStatus(false);
                    myResult.add("message", "该用户未对该条动态评论，不能删除别人的评论");
                } else {
                    if (userId.equals(commentSerive.getUserId(commentId))) {
                        commentSerive.deleteComment(commentId);
                        myResult.changeStatus(true);
                        myResult.add("message", "");
                    } else {
                        myResult.changeStatus(false);
                        myResult.add("message", "不能删除别人的评论");
                    }
                }
            }
        }
        return myResult;
    }

    @PostMapping("/delMoment")
    //必填
    @ApiOperation("删除动态")
    public MyResult delMoment(@RequestParam String userId, @RequestParam int dynamicId) {
        MyResult myResult = new MyResult();
        if ("".equals(userId)) {
            myResult.changeStatus(false);
            myResult.add("message", "用户id不能为空");
            return myResult;
        }
        List<User> user = userService.selectUserbyId(userId);
        if (user == null) {
            myResult.changeStatus(false);
            myResult.add("message", "该用户不存在");
        } else if (user.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "存在多个该用户信息");
        } else {
            Dynamic dynamic = dynamicSerive.selectDynamicByDynamicId(dynamicId);
            if (dynamic == null) {
                myResult.changeStatus(false);
                myResult.add("message", "要删除的动态不存在");
            } else {
                if (userId.equals(dynamic.getUserId())) {
                    dynamic.setDynamicState(3);
                    dynamicSerive.updateDynamic(dynamic);
                    myResult.changeStatus(true);
                    myResult.add("message", "");
                } else {
                    myResult.changeStatus(false);
                    myResult.add("message", "不能删除别人的动态");
                }
            }
        }
        return myResult;
    }

    @PostMapping("/getUsersMoments")
    //必填
    @ApiOperation("获取用户动态")
    public MyResult getUsersMoments(@RequestParam String userId, @RequestParam String targetUserId, @RequestParam int lastMomentId, @RequestParam int length) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(targetUserId)) {
            myResult.changeStatus(false);
            myResult.add("message", "源用户id，目标用户id不能为空");
            return myResult;
        }
        List<User> user = userService.selectUserbyId(userId);
        List<User> user1 = userService.selectUserbyId(targetUserId);
        if (user == null) {
            myResult.changeStatus(false);
            myResult.add("message", "没有源用户");
        } else if (user.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "源用户信息多于一个");
        } else {
            if (user1 == null) {
                myResult.changeStatus(false);
                myResult.add("message", "没有目标用户");
            } else if (user1.size() > 1) {
                myResult.changeStatus(false);
                myResult.add("message", "目标用户多于一个");
            } else {
                if (userId.equals(targetUserId)) {
                    if (lastMomentId == -1) {
                        List<Dynamic> allDynamics = dynamicSerive.selectDynamicByUserIdAnd2State(targetUserId, 1, 2);
                        if (allDynamics.size() == 0) {
                            myResult.changeStatus(true);
                            myResult.add("message", "");
                            return myResult;
                        }
                        List<Map<String, Object>> tmp = new ArrayList<>();
                        for (Dynamic dynamic : allDynamics) {
                            if (length == 0) {
                                break;
                            }
                            Map<String, Object> map = new HashMap<>(4);
                            map.put("momentId", dynamic.getDynamicId());
                            map.put("userID", user1.get(0).getUserId());
                            map.put("userName", user1.get(0).getUserName());
                            map.put("userAvatar", user1.get(0).getAvatarURL());
                            map.put("time", dynamic.getDynamicTime());
                            map.put("text", dynamic.getText());
                            map.put("likedNum", dynamic.getThumbNum());
                            map.put("commentNum", dynamic.getCommentNum());
                            map.put("isLiked", thumbSerive.isThumb(userId, dynamic.getDynamicId()));
                            map.put("isDel", dynamic.getDynamicState() == 3);
                            map.put("tag", dynamic.getDynamicType());
                            String dynamicType = dynamic.getDynamicType();
                            map.put("appendixType", dynamic.getDynamicType());
                            if (dynamicType.equals("1")) {
                                String[] s = dynamic.getDynamicContent().split(";");
                                int imageLength = s.length;
                                if (imageLength == 0) {
                                    myResult.changeStatus(false);
                                    myResult.add("message", "动态中不包含图片");
                                    return myResult;
                                } else {
                                    map.put("photos", dynamic.getDynamicContent());
                                }
                            } else if (dynamicType.equals("2")) {
                                map.put("video", dynamic.getDynamicContent());
                            } else if (dynamicType.equals("3") || dynamicType.equals("4")) {
                                map.put("momentId", dynamic.getDynamicId());
                            } else if (dynamicType.equals("0")) {

                            } else {
                                myResult.changeStatus(false);
                                myResult.add("message", "动态类型码错误");
                                return myResult;
                            }
                            tmp.add(map);
                            --length;
                        }
                        myResult.changeStatus(true);
                        myResult.add("message", tmp);

                    } else {
                        List<Dynamic> dynamics = dynamicSerive.selectDynamicByUserIdAndDynamicIdLimit20(targetUserId, lastMomentId, length);
                        if (dynamics.size() == 0) {
                            myResult.changeStatus(true);
                            myResult.add("message", "");
                            return myResult;
                        } else {
                            List<Map<String, Object>> tmp = new ArrayList<>();
                            for (Dynamic dynamic : dynamics) {
                                if (dynamic.getDynamicType().equals("3") || dynamic.getDynamicType().equals("0") || dynamic.getDynamicType().equals("-1")) {
                                    continue;
                                }
                                Map<String, Object> map = new HashMap<>(4);
                                map.put("momentId", dynamic.getDynamicId());
                                map.put("userID", user1.get(0).getUserId());
                                map.put("userName", user1.get(0).getUserName());
                                map.put("userAvatar", user1.get(0).getAvatarURL());
                                map.put("time", dynamic.getDynamicTime());
                                map.put("text", dynamic.getText());
                                map.put("likedNum", dynamic.getThumbNum());
                                map.put("commentNum", dynamic.getCommentNum());
                                map.put("isLiked", thumbSerive.isThumb(userId, dynamic.getDynamicId()));
                                map.put("isDel", dynamic.getDynamicState() == 3);
                                map.put("tag", dynamic.getDynamicType());
                                String dynamicType = dynamic.getDynamicType();
                                map.put("appendixType", dynamic.getDynamicType());

                                if (dynamicType.equals("1")) {
                                    int imageLength = dynamic.getDynamicContent().split(";").length;
                                    if (imageLength == 0) {
                                        myResult.changeStatus(false);
                                        myResult.add("message", "动态中不包含图片");
                                        return myResult;
                                    } else {
                                        map.put("photos", dynamic.getDynamicContent());
                                    }
                                } else if (dynamicType.equals("2")) {
                                    map.put("video", dynamic.getDynamicContent());
                                } else if (dynamicType.equals("3") || dynamicType.equals("4")) {
                                    map.put("momentId", dynamic.getDynamicId());
                                } else if (dynamicType.equals("0")) {
                                } else {
                                    myResult.changeStatus(false);
                                    myResult.add("message", "动态类型码错误");
                                    return myResult;
                                }
                                tmp.add(map);
                            }
                            myResult.changeStatus(true);
                            myResult.add("message", tmp);
                        }
                    }
                } else {
                    if (lastMomentId == -1) {
                        List<Dynamic> dynamics = dynamicSerive.selectDynamicByUserIdAndState(targetUserId, 2);
                        if (dynamics.size() == 0) {
                            myResult.changeStatus(true);
                            myResult.add("message", "该用户无动态");
                        } else {
                            List<Map<String, Object>> tmp = new ArrayList<>();
                            for (Dynamic dynamic : dynamics) {
                                if (length == 0) {
                                    break;
                                }
                                Map<String, Object> map = new HashMap<>(4);
                                map.put("momentId", dynamic.getDynamicId());
                                map.put("userID", user1.get(0).getUserId());
                                map.put("userName", user1.get(0).getUserName());
                                map.put("userAvatar", user1.get(0).getAvatarURL());
                                map.put("time", dynamic.getDynamicTime());
                                map.put("text", dynamic.getText());
                                map.put("likedNum", dynamic.getThumbNum());
                                map.put("commentNum", dynamic.getCommentNum());
                                map.put("isLiked", thumbSerive.isThumb(userId, dynamic.getDynamicId()));
                                map.put("isDel", dynamic.getDynamicState() == 3);
                                map.put("tag", dynamic.getDynamicType());
                                String dynamicType = dynamic.getDynamicType();
                                map.put("appendixType", dynamic.getDynamicType());

                                if (dynamicType.equals("1")) {
                                    int imageLength = dynamic.getDynamicContent().split(";").length;
                                    if (imageLength == 0) {
                                        myResult.changeStatus(false);
                                        myResult.add("message", "动态中不包含图片");
                                        return myResult;
                                    } else {
                                        map.put("photos", dynamic.getDynamicContent());
                                    }
                                } else if (dynamicType.equals("2")) {
                                    map.put("video", dynamic.getDynamicContent());
                                } else if (dynamicType.equals("3") || dynamicType.equals("4")) {
                                    map.put("momentId", dynamic.getDynamicId());
                                } else if (dynamicType.equals("0")) {
                                } else {
                                    myResult.changeStatus(false);
                                    myResult.add("message", "动态类型码错误");
                                    return myResult;
                                }
                                tmp.add(map);
                                --length;
                            }
                            myResult.changeStatus(true);
                            myResult.add("message", tmp);
                        }
                    } else {
                        List<Dynamic> dynamics = dynamicSerive.selectDynamicByUserIdAndState(targetUserId, 2);
                        if (dynamics.size() == 0) {
                            myResult.changeStatus(false);
                            myResult.add("message", "该用户无动态");
                        } else {
                            List<Map<String, Object>> tmp = new ArrayList<>();
                            for (Dynamic dynamic : dynamics) {
                                if (dynamic.getDynamicId() >= lastMomentId) {
                                    continue;
                                } else {
                                    if (length == 0) {
                                        break;
                                    }

                                    Map<String, Object> map = new HashMap<>(4);
                                    map.put("momentId", dynamic.getDynamicId());
                                    map.put("userID", user1.get(0).getUserId());
                                    map.put("userName", user1.get(0).getUserName());
                                    map.put("userAvatar", user1.get(0).getAvatarURL());
                                    map.put("time", dynamic.getDynamicTime());
                                    map.put("text", dynamic.getText());
                                    map.put("likedNum", dynamic.getThumbNum());
                                    map.put("commentNum", dynamic.getCommentNum());
                                    map.put("isLiked", thumbSerive.isThumb(userId, dynamic.getDynamicId()));
                                    map.put("isDel", dynamic.getDynamicState() == 3);
                                    map.put("tag", dynamic.getDynamicType());
                                    String dynamicType = dynamic.getDynamicType();
                                    map.put("appendixType", dynamic.getDynamicType());
                                    if (dynamicType.equals("1")) {
                                        int imageLength = dynamic.getDynamicContent().split(";").length;
                                        if (imageLength == 0) {
                                            myResult.changeStatus(false);
                                            myResult.add("message", "动态中不包含图片");
                                            return myResult;
                                        } else {
                                            map.put("photos", dynamic.getDynamicContent());
                                        }
                                    } else if (dynamicType.equals("2")) {
                                        map.put("video", dynamic.getDynamicContent());
                                    } else if (dynamicType.equals("3") || dynamicType.equals("4")) {
                                        map.put("momentId", dynamic.getDynamicId());
                                    } else if (dynamicType.equals("0")) {
                                    } else {
                                        myResult.changeStatus(false);
                                        myResult.add("message", "动态类型码错误");
                                        return myResult;
                                    }
                                    tmp.add(map);
                                }
                                --length;
                            }
                            myResult.changeStatus(true);
                            myResult.add("message", tmp);
                        }
                    }
                }
            }
        }
        return myResult;
    }

    @PostMapping("/getTagMoments")
    //必填
    @ApiOperation("获取某类型动态")  //tag-希望获取的动态类型，即dynamicType
    public MyResult getTagMoments(@RequestParam String userId, @RequestParam String tag, @RequestParam int lastMomentId, @RequestParam int length) {
        MyResult myResult = new MyResult();
        if (length == 0) {
            myResult.changeStatus(false);
            myResult.add("message", "长度异常");
            return myResult;
        }
//        if (dynamicSerive.selectDynamicByDynamicId(lastMomentId) == null) {
//            myResult.changeStatus(false);
//            myResult.add("message", "传入的lastMomentId有误，不存在该条动态");
//            return myResult;
//        }
        if ("".equals(userId)) {
            myResult.changeStatus(false);
            myResult.add("message", "用户id不能为空");
            return myResult;
        }
        List<User> user = userService.selectUserbyId(userId);
        if (user == null) {
            myResult.changeStatus(false);
            myResult.add("message", "该用户不存在");
        } else if (user.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "存在多个该用户信息");
        } else {
            if (lastMomentId == -1) {
                //选出所有符合dynamicState、dynamicType的动态
                List<Dynamic> allRequestedDynamics = dynamicSerive.selectDynamicByDynamicStateAndDynamicType(2, tag);
                if (allRequestedDynamics.size() == 0) {
                    myResult.changeStatus(false);
                    myResult.add("message", "该类型动态为空");
                    return myResult;
                }
                int beginDynamicId = allRequestedDynamics.get(allRequestedDynamics.size() - 1).getDynamicId();
                //找到beginDynamicId以后，从此处开始挑动态，长度为length，放入list
                List<Dynamic> dynamics = dynamicSerive.selectDynamicByDynamicIdAndDynamicStateAndDynamicTypeLimitNUM(
                        beginDynamicId, 2, tag, length);
                if (dynamics.size() == 0) {
                    myResult.changeStatus(false);
                    myResult.add("message", "该动态后面无动态");
                } else {
                    List<Map<String, Object>> tmp = new ArrayList<>();
                    for (Dynamic dynamic : dynamics) {
                        Map<String, Object> map = new HashMap<>(4);
                        map.put("momentId", dynamic.getDynamicId());
                        map.put("userID", userId);
                        map.put("userName", user.get(0).getUserName());
                        map.put("userAvatar", user.get(0).getAvatarURL());
                        map.put("time", dynamic.getDynamicTime());
                        map.put("text", dynamic.getText());
                        map.put("likedNum", dynamic.getThumbNum());
                        map.put("commentNum", dynamic.getCommentNum());
                        map.put("isLiked", thumbSerive.isThumb(userId, dynamic.getDynamicId()));
                        map.put("isDel", dynamic.getDynamicState() == 3);
                        map.put("tag", dynamic.getDynamicType());
                        String dynamicType = dynamic.getDynamicType();
                        map.put("appendixType", dynamic.getDynamicType());

                        if ("".equals(dynamic.getDynamicContent())) {
                            myResult.changeStatus(false);
                            myResult.add("message", "该条动态内容为空");
                            return myResult;
                        } else {
                            if (dynamicType.equals("1")) {
                                map.put("photos", dynamic.getDynamicContent());
                            } else if (dynamicType.equals("2")) {
                                map.put("video", dynamic.getDynamicContent());
                            } else if (dynamicType.equals("3") || dynamicType.equals("4")) {
                                map.put("momentId", dynamic.getDynamicId());
                            } else if (dynamicType.equals("0")) {
                            } else {
                                myResult.changeStatus(false);
                                myResult.add("message", "动态类型码错误");
                                return myResult;
                            }
                        }
                        tmp.add(map);
                    }
                    myResult.changeStatus(true);
                    myResult.add("message", tmp);
                }
            } else {
                //挑出lastMomentId后所有符合条件的动态，长度为length
                List<Dynamic> dynamics = dynamicSerive.selectDynamicByDynamicIdAndDynamicStateAndDynamicTypeLimitNUM(
                        lastMomentId, 2, tag, length);
                if (dynamics == null) {
                    myResult.changeStatus(true);
                    myResult.add("message", "");
                } else {
                    List<Map<String, Object>> tmp = new ArrayList<>();
                    for (Dynamic dynamic : dynamics) {
                        Map<String, Object> map = new HashMap<>(4);
                        map.put("momentId", dynamic.getDynamicId());
                        map.put("userID", userId);
                        map.put("userName", user.get(0).getUserName());
                        map.put("userAvatar", user.get(0).getAvatarURL());
                        map.put("time", dynamic.getDynamicTime());
                        map.put("text", dynamic.getText());
                        map.put("likedNum", dynamic.getThumbNum());
                        map.put("commentNum", dynamic.getCommentNum());
                        map.put("isLiked", thumbSerive.isThumb(userId, dynamic.getDynamicId()));
                        map.put("isDel", dynamic.getDynamicState() == 3);
                        map.put("tag", dynamic.getDynamicType());
                        String dynamicType = dynamic.getDynamicType();
                        map.put("appendixType", dynamic.getDynamicType());

                        if ("".equals(dynamic.getDynamicContent())) {
                            myResult.changeStatus(false);
                            myResult.add("message", "该条动态内容为空");
                            return myResult;
                        } else {
                            if (dynamicType.equals("1")) {
                                map.put("photos", dynamic.getDynamicContent());
                            } else if (dynamicType.equals("2")) {
                                map.put("video", dynamic.getDynamicContent());
                            } else if (dynamicType.equals("3") || dynamicType.equals("4")) {
                                map.put("momentId", dynamic.getDynamicId());
                            } else if (dynamicType.equals("0")) {
                            } else {
                                myResult.changeStatus(false);
                                myResult.add("message", "动态类型码错误");
                                return myResult;
                            }
                        }
                        tmp.add(map);
                    }
                    myResult.changeStatus(true);
                    myResult.add("message", tmp);
                }
            }
        }
        return myResult;
    }

    @PostMapping("reportMoment")
    @ApiOperation("举报动态")
    public MyResult reportMoment(@RequestParam String userId, @RequestParam int dynamicId, @RequestParam String message) {
        MyResult myResult = new MyResult();
        if ("".equals(userId)) {
            myResult.changeStatus(false);
            myResult.add("message", "用户id不能为空");
            return myResult;
        }
        List<User> user = userService.selectUserbyId(userId);
        if (user == null) {
            myResult.changeStatus(false);
            myResult.add("message", "该用户不存在");
        } else if (user.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "存在多个该用户信息");
        } else {
            Dynamic dynamic = dynamicSerive.selectDynamicByDynamicId(dynamicId);
            if (dynamic == null) {
                myResult.changeStatus(false);
                myResult.add("message", "要举报的动态不存在");
            } else if (userId.equals(dynamic.getUserId())) {
                myResult.changeStatus(false);
                myResult.add("message", "不能举报自己的动态");
            } else {
                TipOffDynamic tipOffDynamic = new TipOffDynamic();
                tipOffDynamic.setDynamicId(dynamicId);
                tipOffDynamic.setInformerId(userId);
                Date date = new Date(System.currentTimeMillis());
                Timestamp timeStamp = new Timestamp(date.getTime());
                tipOffDynamic.setTipOffTime(timeStamp);
                tipOffDynamic.setTipOffContent(message);
                boolean userIsTipOff = false;
                List<TipOffDynamic> tipOffDynamicList = tipOffDynamicSerive.selectTipOffByDynamicId(tipOffDynamic.getDynamicId());
                if (tipOffDynamicList.size() == 0) {
                    tipOffDynamicSerive.insertTipOff(tipOffDynamic);
                    myResult.changeStatus(true);
                    myResult.add("message", "");
                } else {
                    for (int i = 0; i < tipOffDynamicList.size(); i++) {
                        if (tipOffDynamicList.get(i).getInformerId().equals(tipOffDynamic.getInformerId())) {
                            userIsTipOff = true;
                        }
                    }
                    if (!userIsTipOff) {
                        tipOffDynamicSerive.insertTipOff(tipOffDynamic);
                        myResult.changeStatus(true);
                        myResult.add("message", "");
                    } else {
                        myResult.changeStatus(false);
                        myResult.add("message", "不能重复举报动态");
                    }
                }
            }
        }
        return myResult;
    }


    @PostMapping("/createMomentWithCodeCLI")
    //必填
    @ApiOperation("发表含CLI程序段的⽂本动态")
    public MyResult createMomentWithCodeCLI(@RequestParam String userId, @RequestParam String text, @RequestParam String tag,
                                            @RequestParam String language, @RequestParam String code, @RequestParam String para) {
        MyResult myResult = new MyResult();
        if ("".equals(userId)) {
            myResult.changeStatus(false);
            myResult.add("message", "用户id不能为空");
            return myResult;
        }
        List<User> user = userService.selectUserbyId(userId);
        if (user == null) {
            myResult.changeStatus(false);
            myResult.add("message", "该用户不存在");
        } else if (user.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "存在多个该用户信息");
        } else {
            if ("".equals(language) || "".equals(code)) {
                myResult.changeStatus(false);
                myResult.add("message", "language, code均不能为空");
            } else {
                //dynamic基本属性的设置
                Dynamic dynamic = new Dynamic();
                dynamic.setUserId(userId);
                dynamic.setDynamicIndex(tag);
                dynamic.setDynamicType("4");
                dynamic.setDynamicState(2);
                if (!"".equals(text)) {
                    dynamic.setText(text);
                } else {
                    dynamic.setText("");
                }
                Date date = new Date(System.currentTimeMillis());
                Timestamp timeStamp = new Timestamp(date.getTime());
                dynamic.setDynamicTime(timeStamp);
                //设定code相关属性
                dynamic.setLanguage_(language);
                dynamic.setCode(code);
                dynamic.setPara(para);
                dynamicSerive.insertDynamic(dynamic);
                myResult.changeStatus(true);
                myResult.add("message", "");
            }
        }
        return myResult;
    }

    @PostMapping("/createMomentWithCodeGUI")
    //必填
    @ApiOperation("发表含GUI程序段的⽂本动态")
    public MyResult createMomentWithCodeGUI(@RequestParam String userId, @RequestParam String text, @RequestParam String tag,
                                            @RequestParam String language, @RequestParam String code, @RequestParam String html,
                                            @RequestParam String css, @RequestParam String para) {
        MyResult myResult = new MyResult();
        if ("".equals(userId)) {
            myResult.changeStatus(false);
            myResult.add("message", "用户id不能为空");
            return myResult;
        }
        List<User> user = userService.selectUserbyId(userId);
        if (user == null) {
            myResult.changeStatus(false);
            myResult.add("message", "该用户不存在");
        } else if (user.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "存在多个该用户信息");
        } else {
            if ("".equals(language) || "".equals(code) || "".equals(html) || "".equals(css)) {
                myResult.changeStatus(false);
                myResult.add("message", "language, code, html, css等均不能为空");
            } else {
                //dynamic基本属性的设置
                Dynamic dynamic = new Dynamic();
                dynamic.setUserId(userId);
                dynamic.setDynamicIndex(tag);
                dynamic.setDynamicType("3");
                dynamic.setDynamicState(2);
                if (!"".equals(text)) {
                    dynamic.setText(text);
                } else {
                    dynamic.setText("");
                }
                Date date = new Date(System.currentTimeMillis());
                Timestamp timestamp = new Timestamp(date.getTime());
                dynamic.setDynamicTime(timestamp);
                //code相关内容的设置
                dynamic.setLanguage_(language);
                dynamic.setCode(code);
                dynamic.setHtml(html);
                dynamic.setCss(css);
                dynamic.setPara(para);
                dynamicSerive.insertDynamic(dynamic);
                myResult.changeStatus(true);
                myResult.add("message", "");
            }
        }
        return myResult;
    }

    @PostMapping("/getAppCLI")
    //必填
    @ApiOperation("获取CLI程序段")
    public MyResult getAppCLI(@RequestParam int appId) {
        MyResult myResult = new MyResult();
        Dynamic dynamic = dynamicSerive.selectDynamicByDynamicId(appId);
        if (dynamic == null) {
            myResult.changeStatus(false);
            myResult.add("message", "没有该app");
        } else {
            if (dynamic.getDynamicType().equals("4")) {
                Map<String, Object> map = new HashMap<>(4);
                map.put("appendixType", dynamic.getDynamicType());
                map.put("language", dynamic.getLanguage_());
                map.put("code", dynamic.getCode());
                map.put("para", dynamic.getPara());
                myResult.changeStatus(true);
                myResult.add("message", map);
            } else {
                myResult.changeStatus(false);
                myResult.add("message", "该动态类型不是CLI");
            }
        }
        return myResult;
    }

    @PostMapping("/getAppGUI")
    //必填
    @ApiOperation("获取GUI程序段")
    public MyResult getAppGUI(@RequestParam int appId) {
        MyResult myResult = new MyResult();
        Dynamic dynamic = dynamicSerive.selectDynamicByDynamicId(appId);
        if (dynamic == null) {
            myResult.changeStatus(false);
            myResult.add("message", "没有该app");
        } else {
            if (dynamic.getDynamicType().equals("3")) {
                Map<String, Object> map = new HashMap<>(4);
                map.put("appendixType", dynamic.getDynamicType());
                map.put("text", dynamic.getText());
                map.put("language", dynamic.getLanguage_());
                map.put("code", dynamic.getCode());
                map.put("html", dynamic.getHtml());
                map.put("css", dynamic.getCss());
                map.put("para", dynamic.getPara());
                myResult.changeStatus(true);
                myResult.add("message", map);
            } else {
                myResult.changeStatus(false);
                myResult.add("message", "该动态类型不是GUI");
            }
        }
        return myResult;
    }


}
