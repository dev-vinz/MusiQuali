'use strict'

const tabDifficulties = [];
const tabGenres = [];

let startButtons;
let resetButtons;
let buttonIcons;
let players;
let currentTimeSpans;
let sliders;
let durationSpans;

const zip = (...arr) => Array(Math.max(...arr.map(a => a.length))).fill().map((_, i) => arr.map(a => a[i]));

const calculateTime = (secs) => {
	const minutes = Math.floor(secs / 60);
	const seconds = Math.floor(secs % 60);
	const returnedSeconds = seconds < 10 ? `0${seconds}` : `${seconds}`;
	return `${minutes}:${returnedSeconds}`;
}

function filterMusics() {
	// Gets elements
	const searchbox = document.getElementById('searchbox');
	const cards = document.getElementsByClassName('col-lg-6');
	const results = document.getElementById('nb_results');

	// Display or not corresponding cards
	const filter = searchbox.value.toLowerCase();
	let nbResults = 0;

	for (let i = 0; i < cards.length; i++) {
		const card = cards[i];

		const title = card.querySelector('.card-body .card-title').innerHTML.toLowerCase();
		const subtitle = card.querySelector('.card-body .card-subtitle').innerHTML.toLowerCase();
		const difficulty = card.querySelector('.card-body .difficulty').innerHTML;
		const genres = [...card.querySelectorAll('.card-body .genre')].map(g => g.innerHTML);

		if (tabDifficulties.includes(difficulty) && genres.find(g => tabGenres.includes(g))) {
			if (title.indexOf(filter) > -1 || subtitle.indexOf(filter) > -1) {
				card.classList.remove('d-none');
				nbResults++;
			} else {
				card.classList.add('d-none');
			}
		} else {
			card.classList.add('d-none');
		}
	}

	results.innerText = `${nbResults} résultat${nbResults > 1 ? 's' : ''}`;
}

window.onload = function() {
	/* FILTER */
	const difficultyCheckboxes = document.querySelectorAll('input[type=checkbox][name=difficulty]');
	const genreCheckboxes = document.querySelectorAll('input[type=checkbox][name=genre]');

	for (var checkbox of difficultyCheckboxes) {
		if (checkbox.checked) tabDifficulties.push(checkbox.value);

		checkbox.addEventListener('change', function(event) {
			if (event.target.checked) {
				tabDifficulties.push(event.target.value);
			}
			else {
				const index = tabDifficulties.indexOf(event.target.value);
				tabDifficulties.splice(index, 1);
			}

			filterMusics();
		});
	}

	for (var checkbox of genreCheckboxes) {
		if (checkbox.checked) tabGenres.push(checkbox.value);

		checkbox.addEventListener('change', function(event) {
			if (event.target.checked) {
				tabGenres.push(event.target.value);
			}
			else {
				const index = tabGenres.indexOf(event.target.value);
				tabGenres.splice(index, 1);
			}

			filterMusics();
		});
	}

	filterMusics();

	/* MUSICS */
	startButtons = document.querySelectorAll('.mq-music-buttons .play-button');
	resetButtons = document.querySelectorAll('.mq-music-buttons .reset-button');
	buttonIcons = document.querySelectorAll('.mq-music-buttons .play-button i');

	players = document.querySelectorAll('audio');

	currentTimeSpans = document.querySelectorAll('.mq-music-slider .current-time');
	sliders = document.querySelectorAll('.mq-music-slider input[type=range]');
	durationSpans = document.querySelectorAll('.mq-music-slider .duration');

	zip(players, currentTimeSpans, sliders, durationSpans, startButtons, resetButtons, buttonIcons).forEach(tab => {
		const player = tab[0];
		const currentTime = tab[1];
		const slider = tab[2];
		const duration = tab[3];
		const startButton = tab[4];
		const resetButton = tab[5];
		const buttonIcon = tab[6];

		let musicPlayed = false;

		duration.textContent = calculateTime(player.duration);
		slider.value = 0;
		slider.max = Math.floor(player.duration);

		/* EVENTS */

		startButton.onclick = function() {
			if (musicPlayed) {
				player.pause();

				buttonIcon.classList.add("bi-play-circle");
				buttonIcon.classList.remove("bi-pause-circle");

				musicPlayed = false;
			}
			else {
				player.play();

				buttonIcon.classList.remove("bi-play-circle");
				buttonIcon.classList.add("bi-pause-circle");

				musicPlayed = true;
			}
		}

		resetButton.onclick = function() {
			player.currentTime = 0;
		}

		slider.oninput = function() {
			currentTime.textContent = calculateTime(slider.value);
			player.currentTime = slider.value;
		}

		player.ontimeupdate = function() {
			slider.value = Math.floor(player.currentTime);
			currentTime.textContent = calculateTime(slider.value);

			if (Math.floor(player.currentTime) == Math.floor(player.duration)) {
				buttonIcon.classList.add("bi-play-circle");
				buttonIcon.classList.remove("bi-pause-circle");

				musicPlayed = false;
			}
		}
	});

}