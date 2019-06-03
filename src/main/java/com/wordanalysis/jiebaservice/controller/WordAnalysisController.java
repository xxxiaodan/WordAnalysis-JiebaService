package com.wordanalysis.jiebaservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.wordanalysis.jiebaservice.util.JiebaProperties;
import com.wordanalysis.jiebaservice.util.Res;

import com.wordanalysis.jiebaservice.util.Segmentor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XiaoDan 2019-5
 */
@Api(tags="词法分析器")
@RestController("/WordAnalysis")
@EnableConfigurationProperties(JiebaProperties.class)
public class WordAnalysisController {
    @Autowired
    private JiebaProperties jiebaProperties;


    @ApiOperation("JiebaService-file")
    @RequestMapping(value="/JiebaService-file",method= RequestMethod.POST)
    public Res wordAnalysis(@ApiParam("待处理数据(文件)") @RequestParam MultipartFile text_file,
                            @ApiParam("文本长度上限(默认：500Kb)") @RequestParam(defaultValue = "500") Long max_input,
                            @ApiParam("分词模式(0:默认，Index模式；1：Search模式)") @RequestParam(defaultValue = "0") int segment_mode)
                            throws IOException {
        if (text_file.isEmpty()) return Res.error("上传文件为空");
        if (text_file.getSize() > max_input*1024) return Res.error("上传文件超过上限(<=" + max_input + "Kb)");

        String text = new String(text_file.getBytes(),"utf-8");
        String sentence = text.replace("\r\n","");

        //String filePath = jiebaProperties.getMyDictionaryPath() + customdict_file.getOriginalFilename();
        //File tempFile = new File(filePath);
       //FileUtils.copyInputStreamToFile(customdict_file.getInputStream(), tempFile);

        JSONObject sent_obj = Segmentor.get_Tokens(sentence, segment_mode);
        //tempFile.delete();
        return Res.ok().put("result",sent_obj);
    }

    @ApiOperation("JiebaService-text")
    @RequestMapping(value="/JiebaService-text",method= RequestMethod.POST)
    public Res wordAnalysis(@ApiParam("待处理数据(文本)") @RequestParam String sentence,
                            @ApiParam("文本长度上限(默认：200)") @RequestParam(defaultValue = "200") int max_input,
                            @ApiParam("分词模式(0:默认，Index模式；1：Search模式)") @RequestParam(defaultValue = "0") int segment_mode)
            throws IOException {

        if (sentence.length() > max_input) return Res.error("输入文本长度超过上限" + "(≤" + max_input + ")");
        sentence = sentence.replace("\r\n","");

        //String filePath = jiebaProperties.getMyDictionaryPath() + customdict_file.getOriginalFilename();
        //File tempFile = new File(filePath);
        //FileUtils.copyInputStreamToFile(customdict_file.getInputStream(), tempFile);

        JSONObject sent_obj = Segmentor.get_Tokens(sentence, segment_mode);
        //tempFile.delete();
        return Res.ok().put("result",sent_obj);
    }
}

