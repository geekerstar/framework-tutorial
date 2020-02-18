package com.geekerstar.hutool.core.img;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import com.geekerstar.hutool.common.Constant;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author geekerstar
 * @date 2020/2/18 17:53
 * @description
 */
public class ImgUtilTest {

    /**
     * scale 缩放图片
     */
    @Test
    public void scale() {
        ImgUtil.scale(
                FileUtil.file(Constant.path + "core/img/face.jpg"),
                FileUtil.file(Constant.path + "core/img/scale.jpg"),
                0.5f//缩放比例
        );
    }


    /**
     * cut 剪裁图片
     */
    @Test
    public void cut() {
        ImgUtil.cut(
                FileUtil.file(Constant.path + "core/img/face.jpg"),
                FileUtil.file(Constant.path + "core/img/cut.jpg"),
                new Rectangle(200, 200, 100, 100)//裁剪的矩形区域
        );
    }

    /**
     * slice 按照行列剪裁切片（将图片分为20行和20列）
     */
    @Test
    public void slice() {
        ImgUtil.slice(
                FileUtil.file(Constant.path + "core/img/face.jpg"),
                FileUtil.file(Constant.path + "core/img/"),
                10,
                10);
    }

    /**
     * convert 图片类型转换，支持GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG等
     */
    @Test
    public void convert() {
        ImgUtil.convert(
                FileUtil.file(Constant.path + "core/img/face.jpg"),
                FileUtil.file(Constant.path + "core/img/face.png")
        );
    }

    /**
     * gray 彩色转为黑白
     */
    @Test
    public void gray() {
        ImgUtil.gray(
                FileUtil.file(Constant.path + "core/img/face.jpg"),
                FileUtil.file(Constant.path + "core/img/gray.jpg")
        );
    }

    /**
     * pressText 添加文字水印
     */
    @Test
    public void pressText() {
        ImgUtil.pressText(//
                FileUtil.file(Constant.path + "core/img/face.jpg"), //
                FileUtil.file(Constant.path + "core/img/pressText.jpg"), //
                "版权所有", Color.WHITE, //文字
                new Font("黑体", Font.BOLD, 100), //字体
                0, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                0, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                0.8f//透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
        );

    }


    /**
     * pressImage 添加图片水印
     */
    @Test
    public void pressImage() {
        ImgUtil.pressImage(
                FileUtil.file(Constant.path + "core/img/face.jpg"),
                FileUtil.file(Constant.path + "core/img/pressImage.jpg"),
                ImgUtil.read(FileUtil.file(Constant.path + "core/img/gray.jpg")), //水印图片
                0, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                0, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                0.1f
        );
    }

    /**
     * rotate 旋转图片
     */
    @Test
    public void rotate() throws IOException {
        // 旋转180度
        Image image = ImgUtil.rotate(ImageIO.read(FileUtil.file(Constant.path + "core/img/face.jpg")), 180);
        ImgUtil.write(image, FileUtil.file(Constant.path + "core/img/rotate.jpg"));

    }

    /**
     * flip 水平翻转图片
     */
    @Test
    public void flip() {
        ImgUtil.flip(
                FileUtil.file(Constant.path + "core/img/face.jpg"),
                FileUtil.file(Constant.path + "core/img/flip.jpg")
        );

    }
}
