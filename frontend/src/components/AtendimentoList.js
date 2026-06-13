import React, { useState } from 'react';
import { atendimentoService } from '../services/api';

function AtendimentoList({ atendimentos = [], profissionais = [], onUpdate }) {
  const [form, setForm] = useState({ data: '', horario: '', problemaTexto: '', receitaSaude: '', profissionalDeSaudeId: '' });

  const deletar = async (id) => {
    await atendimentoService.deletar(id);
    if(onUpdate) onUpdate();
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      data: form.data,
      horario: form.horario, // format is HH:mm
      problemaTexto: form.problemaTexto,
      receitaSaude: form.receitaSaude.split(',').map(c => c.trim()).filter(c => c),
      profissionalDeSaude: { id: form.profissionalDeSaudeId }
    };
    await atendimentoService.criar(payload);
    setForm({ data: '', horario: '', problemaTexto: '', receitaSaude: '', profissionalDeSaudeId: '' });
    if(onUpdate) onUpdate();
  };

  return (
    <div className="section-container">
      <h2>Atendimentos</h2>
      
      <form onSubmit={handleSubmit} className="form-container">
        <input type="date" value={form.data} onChange={e => setForm({...form, data: e.target.value})} required />
        <input type="time" value={form.horario} onChange={e => setForm({...form, horario: e.target.value})} />
        <input type="text" placeholder="Problema / Sintomas" value={form.problemaTexto} onChange={e => setForm({...form, problemaTexto: e.target.value})} />
        <input type="text" placeholder="Receita (separada por vírgula)" value={form.receitaSaude} onChange={e => setForm({...form, receitaSaude: e.target.value})} />
        <select value={form.profissionalDeSaudeId} onChange={e => setForm({...form, profissionalDeSaudeId: e.target.value})} required>
          <option value="">Selecione o Profissional</option>
          {profissionais.map(p => (
            <option key={p.id} value={p.id}>{p.nome}</option>
          ))}
        </select>
        <button type="submit">Agendar Atendimento</button>
      </form>

      <table>
        <thead>
          <tr><th>ID</th><th>Data</th><th>Horário</th><th>Problema</th><th>Receita</th><th>Profissional</th><th>Ações</th></tr>
        </thead>
        <tbody>
          {atendimentos.map(a => (
            <tr key={a.id}>
              <td>{a.id}</td>
              <td>{a.data}</td>
              <td>{a.horario}</td>
              <td>{a.problemaTexto}</td>
              <td>{a.receitaSaude?.join(', ')}</td>
              <td>{a.profissionalDeSaude?.nome}</td>
              <td><button className="btn-danger" onClick={() => deletar(a.id)}>Excluir</button></td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AtendimentoList;
