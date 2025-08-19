package com.example.mapping.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mapping.entity.Bus;
import com.example.mapping.entity.Route;
import com.example.mapping.entity.Schedule;
import com.example.mapping.repository.BusRepository;
import com.example.mapping.repository.RouteRepository;
import com.example.mapping.repository.ScheduleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class BusService {
 
	private final BusRepository busRepository;
	private final RouteRepository routeRepository;
	private final ScheduleRepository scheduleRepository;

	public BusService(BusRepository busRepository, RouteRepository routeRepository,
			ScheduleRepository scheduleRepository) {
		this.busRepository = busRepository;
		this.routeRepository = routeRepository;
		this.scheduleRepository = scheduleRepository;
	}

	// ---- Bus ----
	public Bus createBus(Bus bus) {
		return busRepository.save(bus);
	}

	public List<Bus> listBuses() {
		return busRepository.findAll();
	}

	public Bus getBus(Long id) {
		return busRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bus not found"));
	}

	public Bus updateBus(Long id, Bus updated) {
		Bus existing = getBus(id);
		existing.setBusName(updated.getBusName());
		existing.setBusType(updated.getBusType());
		existing.setTotalSeats(updated.getTotalSeats());
		existing.setPricePerSeat(updated.getPricePerSeat());
		return busRepository.save(existing);
	}

	public void deleteBus(Long id) {
		busRepository.deleteById(id);
	}

	// ---- Route ----
	public Route createRoute(Route route) {
		return routeRepository.save(route);
	}

	public List<Route> listRoutes() {
		return routeRepository.findAll();
	}

	public Route getRoute(Long id) {
		return routeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Route not found"));
	}

	public Route updateRoute(Long id, Route updated) {
		Route existing = getRoute(id);
		existing.setSource(updated.getSource());
		existing.setDestination(updated.getDestination());
		existing.setDistanceKm(updated.getDistanceKm());
		return routeRepository.save(existing);
	}

	public void deleteRoute(Long id) {
		routeRepository.deleteById(id);
	}

	// ---- Schedule ----
	public Schedule createSchedule(Long busId, Long routeId, Schedule schedule) {
		Bus bus = getBus(busId);
		Route route = getRoute(routeId);
		schedule.setBus(bus);
		schedule.setRoute(route);
		return scheduleRepository.save(schedule);
	}

	public List<Schedule> listSchedules() {
		return scheduleRepository.findAll();
	}

	public Schedule getSchedule(Long id) {
		return scheduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Schedule not found"));
	}

	public void deleteSchedule(Long id) {
		scheduleRepository.deleteById(id);
	}

	// ---- Search ----
	@Transactional(readOnly = true)
	public List<Schedule> search(String source, String destination, LocalDate date) {
		return scheduleRepository.findByRoute_SourceIgnoreCaseAndRoute_DestinationIgnoreCaseAndTravelDate(source,
				destination, date);
	}
}
