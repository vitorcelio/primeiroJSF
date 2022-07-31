package br.com.primeirojsf.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.primeirojsf.dao.HibernateUtil;
import br.com.primeirojsf.model.Pessoa;

@WebFilter(urlPatterns = {"/*"})
public class FilterAutenticacao implements Filter{
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		
		Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
		
		String url = request.getServletPath();
		
		if(!url.equalsIgnoreCase("index.jsf") && usuarioLogado == null ) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsf?faces-redirect=true");
			dispatcher.forward(req, res);
			return;
		} else {
			chain.doFilter(req, res);	
		}
		
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		HibernateUtil.getEntityManager();
	}
	
}
