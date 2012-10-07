package com.module.webspider.handle;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.management.monitor.Monitor;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;

public class UrlHandle extends Handle {
	
	public UrlHandle(String keyword, String url) {
		super(keyword, url);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Monitor handle() {
		// TODO Auto-generated method stub
		
		try {
			URL url = new URL(this.url);
			System.out.println("--------------------");
			System.out.println(url);
			System.out.println("--------------------");
			URLConnection urlCon = url.openConnection();
			
			DefaultParserFeedback fb = new DefaultParserFeedback();
			Parser parser = new Parser(urlCon,fb);
			
	        HtmlPage hp = new HtmlPage(parser);
	        parser.visitAllNodesWith(hp);
	        NodeList nl=hp.getBody();
	        
	        NodeFilter nf = new NodeClassFilter(TextNode.class);
	        
	        nl=nl.extractAllNodesThatMatch(nf,true);
			for (int n = 0; n < nl.size(); n++) {
			TextNode tn=(TextNode) nl.elementAt(n);
			this.htmlstr+=tn.toHtml(true);
			}
			this.subject=this.adstractSubject(this.htmlstr);
			this.title=hp.getTitle();			
	        //-----------------------------------------
			if(this.subject!=null&&this.addSubject_hash(this.subject)){
				System.out.println(this.url);
				System.out.println(this.subject);
				NodeList nl_a=hp.getBody();
		        NodeFilter nf_name = new TagNameFilter("A");
		        nl_a=nl_a.extractAllNodesThatMatch(nf_name,true);
				for (int n = 0; n < nl_a.size(); n++) {
					LinkTag link = (LinkTag) nl_a.elementAt(n);
					
					// 链接地址
					String url_href=link.getAttribute("href");		
					url_href=this.integrityUrl(url_href);
					this.addSubUrl(url_href);
					//System.out.println(url_href + "\n");
					//UrlHandle urlhandle = new UrlHandle(this.keyword, url_href);
					this.setKeyword(this.keyword);
					this.setUrl(url_href);
					this.handle();
					//System.out.println(url_href + "\n");
					// 链接名称
					//System.out.println(link.getStringText());
				}
				System.out.println(this.subUrl.size());
			}
			
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println("sdfsdf".indexOf("s"));
		// TODO Auto-generated method stub
		System.out.println("starting...");		
		String kw="苏敦快";
		String kwencode=URLEncoder.encode(kw);
		String urlstr="http://www.baidu.com/s?wd="+kwencode;
		System.out.println(urlstr);
		System.out.println("begin==============================");
		UrlHandle urlhandle = new UrlHandle(kw, urlstr);
		urlhandle.handle();
	}

}
