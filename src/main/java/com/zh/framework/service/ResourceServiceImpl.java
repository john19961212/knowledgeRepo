package com.zh.framework.service;

import com.zh.framework.entity.Resource;
import com.zh.framework.entity.Role;
import com.zh.framework.entity.TreeGridData;
import com.zh.framework.mapper.ResourceMapper;
import com.zh.framework.mapper.RoleMapper;
import com.zh.framework.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Letg4 on 2017/9/6.
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<TreeGridData> queryAsTree(String sidx,String sord) {
        List<TreeGridData> totalList = new ArrayList<>();
        toTreeDataList(null,sidx,sord,1, totalList);
        return totalList;
    }


    @Override
    public List<Resource> querySearch(Map<String, Object> param) {
        return resourceMapper.query(param);
    }

    @Override
    public List<Resource> queryByUser(String userid) {
//        List <String> userroles=roleMapper.getUserRole(userid,1);
//        Set<Resource> resSet=new HashSet<>();
//        for(String roleid:userroles ){
//            resSet.addAll(resourceMapper.queryByRole(roleid,1));
//        }
//        return new ArrayList<>(resSet);
        return resourceMapper.queryByUser(userid);
    }

    @Override
    public int checkRepeat(String column, String value) {
        return resourceMapper.checkRepeat(column,value);
    }

    @Override
    public List<String> getResources(String roleId) {
        List<Resource> list = resourceMapper.queryByRole(roleId,1);
        List<String> result = new ArrayList<>();
        for (Resource resource : list)
            result.add(resource.getId());
        return result;
    }

    private int toTreeDataList(String parentId,String sidx,String sord, int level, List<TreeGridData> totalList) {
        List<Resource> list = resourceMapper.queryByPid(parentId,sidx,sord);
        for (Resource res : list) {
            TreeGridData treedata = new TreeGridData(res);
            treedata.setLevel(level);
            treedata.setExpanded(true);
            treedata.setLoaded(true);
            totalList.add(treedata);
            treedata.setLeaf(toTreeDataList(res.getId(),sidx,sord, level + 1, totalList) == 0);
        }
        return list.size();
    }
}
