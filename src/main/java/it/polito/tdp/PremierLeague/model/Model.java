package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	PremierLeagueDAO dao = new PremierLeagueDAO();
	Graph<Match, DefaultWeightedEdge> grafo;
	
	public String creaGrafo(int mese, int minuti) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, dao.getMatchesMese(mese));
		for(Match m1 : this.grafo.vertexSet()) {
			for(Match m2: this.grafo.vertexSet()) {
				if(!m1.equals(m2)) {
				int peso = dao.getPeso(m1, m2, minuti);
				if(peso>0) {
					this.grafo.addEdge(m2, m1); 
					this.grafo.setEdgeWeight(this.grafo.getEdge(m2, m1), peso);					
				}
				}
			}
		}
		
		return String.format("Creato grafo con %d vertici e %d archi.\n",
				this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
		
	}

	public String getPesoMassimo() {
		int max=1;
		List<DefaultWeightedEdge> listE = new ArrayList<>();
		String res= "";
		
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e)>=max) {
				if(this.grafo.getEdgeWeight(e)==max) {
				//max=(int)this.grafo.getEdgeWeight(e);
				res= res + this.grafo.getEdgeSource(e).toString()+" "+
						this.grafo.getEdgeTarget(e)+"\n";
				}else {
					max=(int)this.grafo.getEdgeWeight(e);
					res=  this.grafo.getEdgeSource(e).toString()+" "+
							this.grafo.getEdgeTarget(e)+"\n";
				}
			}
			
		}
		return res;
	}
}
