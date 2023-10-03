package br.ce.wcaquino.taskbackend.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

//@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest {

	@InjectMocks
	private TaskController taskControler;

	@Mock
	private TaskRepo taskRepoMock;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		Task task = new Task();
		task.setDueDate(LocalDate.now());
		task.setId(1L);
		try {
			taskControler.save(task);
			fail("Não deveria salvar");
		} catch (ValidationException e) {
			assertEquals("Fill the task description", e.getMessage());
		}
	}

	@Test
	public void naoDeveSalvarTarefaSemData() {
		Task task = new Task();
		task.setTask("descricao");
		task.setId(1L);
		try {
			taskControler.save(task);
			fail("Não deveria salvar");
		} catch (ValidationException e) {
			assertEquals("Fill the due date", e.getMessage());
		}

	}

	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		Task task = new Task();
		task.setTask("descricao");
		task.setDueDate(LocalDate.now().minusDays(1));
		task.setId(1L);
		try {
			taskControler.save(task);
			fail("Não deveria salvar");
		} catch (ValidationException e) {
			assertEquals("Due date must not be in past", e.getMessage());
		}

	}

	@Test
	public void deeSalvarTarefaComSucesso() throws ValidationException {

		Task task = new Task();
		task.setTask("descricao");
		task.setDueDate(LocalDate.now().plusDays(1));

		when(taskRepoMock.save(task)).thenReturn(task);
		ResponseEntity<Task> taskSaved = taskControler.save(task);

		Mockito.verify(taskRepoMock, times(1)).save(task);
		assertEquals(task.getTask(), taskSaved.getBody().getTask());
	}

}
