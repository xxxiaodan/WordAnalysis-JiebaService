package com.wordanalysis.jiebaservice.util;


import com.alibaba.fastjson.JSONObject;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;


@ApiModel("分词器")
public class Segmentor {
    public static JSONObject get_Tokens(String sentence, int segment_mode){
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
        return sent_obj;
    }

}
