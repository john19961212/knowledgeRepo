package com.zh.framework.controller;

import com.github.pagehelper.PageInfo;
import com.zh.framework.entity.Knowledge;
import com.zh.framework.entity.PageBean;
import com.zh.framework.entity.Response;
import com.zh.framework.service.BaseService;
import com.zh.framework.service.KnowledgeService;
import com.zh.framework.util.Constant;
import com.zh.framework.util.TypeTester;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class KnowledgeController{

    @Autowired
    KnowledgeService knowledgeService;

    /**
     * 分页查询
     *
     * @param page 请求的页码
     * @param rows 数据集
     *
     */
    @RequestMapping("/selectPage.form")
    @ResponseBody
    public PageBean selectPage(@RequestParam(value="page")int page,@RequestParam(value="rows")int rows){
        PageBean pageBean=new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(8);
        pageBean=knowledgeService.queryAllKnowledge(pageBean);
        return pageBean;
    }
    /**
     * 添加知识
     *
     * @param kTitle 知识标题
     * @param createUserId 创建人ID
     * @param kAnswer 知识解答
     *
     */
    @RequestMapping("/addKnowledge.form")
    @ResponseBody
    public void add(@RequestParam(value="kTitle")String kTitle, @RequestParam(value="createUserId")String createUserId,  @RequestParam(value="kAnswer")String kAnswer){

        System.out.println("addKnowledge");
        UUID uuid  =  UUID.randomUUID();
        String id = UUID.randomUUID().toString();
        Knowledge k=new Knowledge();
        //System.out.println(createTime);

        //手动输入时间

//        java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
//        String s= "2011-07-09 ";
//        Date date=null;
//        try{
//            date =  formatter.parse(s);
//        }catch(Exception e){
//            System.out.println("错误");
//        }

        //获取系统时间

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String s=formatter.format(new Date());

        Date date=null;
        try{
            date =  formatter.parse(s);
        }catch(Exception e){
            System.out.println("异常错误");
        }



        k.setId(id);
        k.setkTitle(kTitle);
        k.setkAnswer(kAnswer);
        k.setCreateUserId(createUserId);
        k.setCreateTime(date);

        knowledgeService.addKnowledge(k);

    }

    /**
     * 删除知识（删除待审批）
     *
     * @param id 知识ID
     */
    @RequestMapping("/knowledgeDelete.form")
    @ResponseBody
    public void knowledgeDelete(@RequestParam("id") String id){

        knowledgeService.updateKnowledgeStatus(id,Knowledge.DELETE_WAITING);

    }

    /**
     * 根据id查询知识
     *
     * @param id 知识ID
     */
    @RequestMapping("/queryKnowledgeById.form")
    @ResponseBody
    public Knowledge queryKnowledgeById(@RequestParam("id") String id){

        return knowledgeService.queryKnowledgeById(id);

    }

    /**
     * 编辑更新知识
     *
     * @param kTitle 知识标题
     * @param createUserId 创建人ID
     * @param kAnswer 知识解答
     */
    @RequestMapping("/updateKnowledge.form")
    @ResponseBody
    public void updateKnowledge(@RequestParam(value="id") String id,@RequestParam(value="kTitle")String kTitle, @RequestParam(value="createUserId")String createUserId,  @RequestParam(value="kAnswer")String kAnswer){
        System.out.println(id+kTitle);
        Knowledge k=new Knowledge();
        k.setId(id);
        k.setkTitle(kTitle);
        k.setCreateUserId(createUserId);
        k.setkAnswer(kAnswer);
        knowledgeService.updateKnowledge(id,kTitle,createUserId,kAnswer);

    }


    /**
     * 知识审批
     *
     * @param id 知识ID
     */
    @RequestMapping("/knowledgeApprova.form")
    @ResponseBody
    public void knowledgeApprova(@RequestParam("id") String id,@RequestParam("kApprUserId") String kApprUserId,@RequestParam("kApprMemo") String kApprMemo,@RequestParam("button") String button){

        Knowledge k=knowledgeService.queryKnowledgeById(id);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String s=formatter.format(new Date());

        Date date=null;
        try{
            date =  formatter.parse(s);
        }catch(Exception e){
            System.out.println("异常错误");
        }
        System.out.println(id+kApprUserId+kApprMemo);
        knowledgeService.updateAppr(id,kApprUserId,kApprMemo,date);

        if(button.equals("通过")){


            if(k.getkApprStatus().equals(Knowledge.DELETE_WAITING)){
                knowledgeService.deleteKnowledge(id);
            }else if(k.getkApprStatus().equals(Knowledge.INSERT_WAITING)){
                knowledgeService.updateKnowledgeStatus(id,Knowledge.APPROVED);
            }else if(k.getkApprStatus().equals(Knowledge.UPDATE_WAITING)){
                knowledgeService.updateKnowledgeStatus(id,Knowledge.APPROVED);
            }

        }else if(button.equals("不通过")){

            knowledgeService.updateKnowledgeStatus(id,Knowledge.UNAPPROVED);

        }








    }




}

