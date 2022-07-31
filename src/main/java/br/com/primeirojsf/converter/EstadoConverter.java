package br.com.primeirojsf.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.primeirojsf.dao.EstadoDao;
import br.com.primeirojsf.model.Estado;

@FacesConverter(forClass = Estado.class, value = "estadoConverter")
public class EstadoConverter implements Converter, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private EstadoDao dao = new EstadoDao();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		// TODO Auto-generated method stub
		Estado estado = dao.findById(Long.parseLong(value));
		
		return estado;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		// TODO Auto-generated method stub
		if(value == null) {
			return null;
		} else if (value instanceof Estado) {
			return ((Estado) value).getId().toString();			
		} else {
			return value.toString();
		}
	}
	
}
