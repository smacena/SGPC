package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.sgpc.dlo.MantemTmpDLO;
import br.sgpc.dominio.Tmp;

/**
 * Bean respons�vel por cadastrar um novo tmp, alterar, excluir e visualizar
 * tmps cadastradas.
 */
@ManagedBean(name = "mbMantemTmp")
@SessionScoped
public class MbMantemTmp implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
    private MantemTmpDLO mantemTmpDLO;
	
	private Tmp tmp;
	
	private List<Tmp> listaTmp;
	
	private Boolean modoEdicao;
    
	@PostConstruct
	public void inicializar(){
		tmp      = new Tmp();
		listaTmp = new ArrayList<Tmp>();
		carregarTmp();
		
		modoEdicao = false;
	}
	
	private void carregarTmp(){
		listaTmp = mantemTmpDLO.carregarDados();
	}
	
	public void cadastrar() {
		if (modoEdicao) {
			try {
				mantemTmpDLO.alterar(tmp);
				carregarTmp();

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro alterado com sucesso."));

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
						"Erro ao alterar dados com a seguinte mensagem: " + e.getMessage()));
				
			}
			modoEdicao = false;
			limpar();
		} else {
			try {
				mantemTmpDLO.cadastrar(tmp);
				carregarTmp();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro cadastrado com sucesso!"));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
						"Erro ao cadastrar registro com a seguinte mensagem: " + e.getMessage()));
			}
			limpar();
		}
	}
	
	public void limpar(){
		tmp = new Tmp();
		
		modoEdicao = false;
	}
	
	public void editar(){
		modoEdicao = true;
	}
	
	public void excluir(Tmp tmp){
		try {
			mantemTmpDLO.excluir(tmp);
			carregarTmp();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao deletar registro com a seguinte mensagem: "+e.getMessage()));
		}		
	}

	public Tmp getTmp() {
		return tmp;
	}

	public void setTmp(Tmp tmp) {
		this.tmp = tmp;
	}

	public List<Tmp> getListaTmp() {
		return listaTmp;
	}

	public void setListaTmp(List<Tmp> listaTmp) {
		this.listaTmp = listaTmp;
	}

	public Boolean getModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(Boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}
	
}