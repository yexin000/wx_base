package cn.trustway.weixin.controller;

import cn.trustway.weixin.bean.BaseBean.DELETED;
import cn.trustway.weixin.bean.WxCode;
import cn.trustway.weixin.exception.ServiceException;
import cn.trustway.weixin.model.WxCodeModel;
import cn.trustway.weixin.service.WxCodeService;
import cn.trustway.weixin.util.DateUtil;
import cn.trustway.weixin.util.HtmlUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信参数控制类
 * @author hucheng
 *
 */
@Controller
@RequestMapping("/wxCode")
public class WxCodeController extends BaseController {
	private final static Logger log = Logger.getLogger(WxCodeController.class);

	// Servrice start
	@Autowired(required = false)
	// 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private WxCodeService<WxCode> wxCodeService;

	/**
	 * 首页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView menu(WxCodeModel model, HttpServletRequest request) throws Exception {
		Map<String, Object> context = getRootMap();
		model.setDeleted(DELETED.NO.key);
		List<WxCode> dataList = wxCodeService.queryByList(model);
		// 设置页面数据
		context.put("dataList", dataList);
		return forword("wx/wxCode", context);
	}

	/**
	 * json 列表页面
	 * 
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dataList")
	public void dataList(WxCodeModel model, HttpServletResponse response) throws Exception {
		List<WxCode> dataList = wxCodeService.queryByList(model);
		// 设置页面数据
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", model.getPager().getRowCount());
		jsonMap.put("rows", dataList);
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/queryList")
	public void queryList(WxCodeModel model, HttpServletResponse response) throws Exception {
		List<WxCode> dataList = wxCodeService.queryByAll();
		// 设置页面数据
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", model.getPager().getRowCount());
		jsonMap.put("rows", dataList);
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/queryListByCode")
	public void queryListByCode(String code, HttpServletResponse response) throws Exception {
		WxCode wxCode = wxCodeService.getBeanByCode(code);
		if(wxCode!=null){
			List<WxCode> dataList = wxCodeService.getChildCode(wxCode.getId());
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("total", dataList.size());
			jsonMap.put("rows", dataList);
			HtmlUtil.writerJson(response, jsonMap);
		}
	}


	/**
	 * 添加或修改数据
	 * 
	 * @param bean
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public void save(WxCode bean, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int count = wxCodeService.getCodeCountByCode(bean.getCode());
		if (bean.getId() == null) {
			if (count > 0) {
				throw new ServiceException("参数已存在.");
			}
			bean.setDeleted(DELETED.NO.key);
			bean.setCreateTime(DateUtil.getDateByString(""));
			wxCodeService.add(bean);
		} else {
			wxCodeService.update(bean);
		}
		sendSuccessMessage(response, "保存成功~");
	}

	@RequestMapping("/getId")
	public void getId(Integer id, HttpServletResponse response) throws Exception {
		Map<String, Object> context = new HashMap<String, Object>();
		WxCode bean = wxCodeService.queryById(id);
		if (bean == null) {
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		context.put(SUCCESS, true);
		context.put("data", bean);
		HtmlUtil.writerJson(response, context);
	}

	@RequestMapping("/delete")
	public void delete(Integer[] id, HttpServletResponse response) throws Exception {
		if (id != null && id.length > 0) {
			wxCodeService.delete(id);
			sendSuccessMessage(response, "删除成功");
		} else {
			sendFailureMessage(response, "未选中记录");
		}
	}

	/**
	 * 查询商品主类型列表
	 *
	 * @param response
	 * @return
	 */
	@RequestMapping("/getAuctionItemType")
	public void getAuctionItemType(HttpServletResponse response) {
		List<WxCode> dataList = wxCodeService.getAuctionItemType();
		List<WxCode> dataList2 = wxCodeService.getAuctionItemSecondType(dataList.get(0).getCode());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", dataList.size());
		jsonMap.put("rows1", dataList);

		jsonMap.put("total2", dataList2.size());
		jsonMap.put("rows2", dataList2);

		HtmlUtil.writerJson(response, jsonMap);
	}


	/**
	 * 查询商品二级类型列表
	 *
	 * @param response
	 * @return
	 */
	@RequestMapping("/getAuctionItemSecondType")
	public void getAuctionItemSecondType(String code, HttpServletResponse response) {
		List<WxCode> dataList = wxCodeService.getAuctionItemSecondType(code);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", dataList.size());
		jsonMap.put("rows", dataList);
		HtmlUtil.writerJson(response, jsonMap);
	}
}
