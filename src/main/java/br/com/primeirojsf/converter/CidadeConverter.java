package br.com.primeirojsf.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.primeirojsf.dao.CidadeDao;
import br.com.primeirojsf.model.Cidade;

@FacesConverter(forClass = Cidade.class, value = "cidadeConverter")
public class CidadeConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	
	private CidadeDao dao = new CidadeDao();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		// TODO Auto-generated method stub

		Cidade cidade = dao.findById(Long.parseLong(value));
		return cidade;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		// TODO Auto-generated method stub
		if (value == null) {
			return null;
		} else if (value instanceof Cidade) {
			return ((Cidade) value).getId().toString();
		} else {
			return value.toString();
		}
	}

}
