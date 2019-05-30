package com.wordanalysis.jiebaservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.wordanalysis.jiebaservice.util.JiebaProperties;
import com.wordanalysis.jiebaservice.util.Res;

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


    @ApiOperation("JiebaService")
    @RequestMapping(value="/JiebaService",method= RequestMethod.POST)
    public Res wordAnalysis(@ApiParam("待处理数据") @RequestParam MultipartFile text_file,
                            @ApiParam("分词模式(0:默认，Index模式；1：Search模式)") @RequestParam(defaultValue = "0") int segment_mode)
                            //@ApiParam("用户词典") @RequestParam MultipartFile customdict_file)
                            throws IOException {
        if (text_file.isEmpty()) return Res.error("上传文件为空");

        String text = new String(text_file.getBytes(),"utf-8");
        String sentence = text.replace("\r\n","");

        //String filePath = jiebaProperties.getMyDictionaryPath() + customdict_file.getOriginalFilename();
        //File tempFile = new File(filePath);
       //FileUtils.copyInputStreamToFile(customdict_file.getInputStream(), tempFile);

        JSONObject sent_obj = new JSONObject();
        sent_obj.put("sentence",sentence);
        JiebaSegmenter segmenter = new JiebaSegmenter();

        //if(!text_file.isEmpty()){
        //    sent_obj.put("customdict_use", "yes");
        //}else{
        //    sent_obj.put("customdict_use", "no");
        //}

        List<SegToken> search_tokens;
        if (segment_mode == 0){
            search_tokens = segmenter.process(sentence, JiebaSegmenter.SegMode.SEARCH);
        }else{
            search_tokens = segmenter.process(sentence, JiebaSegmenter.SegMode.INDEX);
        }
        List<JSONObject> item_list = new ArrayList<>();
        for (SegToken token: search_tokens) {
            JSONObject items = new JSONObject();
            items.put("word", token.word);
            items.put("offset", "[" + token.startOffset + ":" + token.endOffset + "]");
            item_list.add(items);
        }
        sent_obj.put("items",item_list);
        //tempFile.delete();
        return Res.ok().put("result",sent_obj);
    }
}

