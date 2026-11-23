# Avito QA Test Task

Тестовое задание на стажировку QA в Авито, осень 2026

## Задание 1

[Баг-репорты по скриншотам](BUGS.md)


## Список дефектов

Список обновляется из [BUGS.md](BUGS.md) после изменений в основной ветке.
Чтобы обновить его локально, выполни `python3 update_bug_index.py`.

<!-- BEGIN BUG INDEX -->
### По приоритету

| Приоритет | ID | Дефект |
| --- | --- | --- |
| P2 | [BUG-002](BUGS.md#bug-002---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D0%BF%D1%80%D0%BE%D0%B4%D0%B0%D0%B2%D1%86%D0%B0-%D1%83%D0%B3%D0%BB%D0%BE%D0%B2%D0%BE%D0%B9-%D0%B4%D0%B8%D0%B2%D0%B0%D0%BD-%D0%BD%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD-%D0%BF%D1%80%D1%8F%D0%BC%D1%8B%D0%BC) | На карточке продавца угловой диван назван прямым |
| P2 | [BUG-003](BUGS.md#bug-003---p2---%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5-%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B0%D1%85-%D0%B4%D0%BB%D0%B8%D0%BD%D0%BD%D0%B5%D0%B5-%D1%87%D0%B5%D0%BC-%D0%B2-%D1%84%D0%BE%D1%80%D0%BC%D0%B5) | Описание на карточках длиннее, чем в форме |
| P2 | [BUG-004](BUGS.md#bug-004---p2---%D0%B7%D0%B0-%D0%BD%D0%B5%D0%B4%D0%B5%D0%BB%D1%8E-%D0%B5%D1%81%D1%82%D1%8C-8-%D0%BF%D1%80%D0%BE%D1%81%D0%BC%D0%BE%D1%82%D1%80%D0%BE%D0%B2-%D0%B0-%D0%B3%D1%80%D0%B0%D1%84%D0%B8%D0%BA-%D0%BF%D1%83%D1%81%D1%82) | За неделю есть 8 просмотров, а график пуст |
| P2 | [BUG-005](BUGS.md#bug-005---p2---%D1%83-%D0%B0%D0%BA%D1%86%D0%B8%D0%B8-%D1%82%D0%B5%D1%85%D0%BD%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%BE%D0%B5-%D0%BD%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5-%D0%B8-%D1%81%D0%BB%D0%BE%D0%BC%D0%B0%D0%BD%D0%BD%D0%B0%D1%8F-%D0%B8%D0%BA%D0%BE%D0%BD%D0%BA%D0%B0) | У акции техническое название и сломанная иконка |
| P3 | [BUG-001](BUGS.md#bug-001---p3---%D0%B4%D0%B2%D0%B0%D0%B6%D0%B4%D1%8B-%D0%BF%D0%BE%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD-%D0%B7%D0%B0%D0%B3%D0%BE%D0%BB%D0%BE%D0%B2%D0%BE%D0%BA-%D0%B2%D0%BD%D0%B5%D1%88%D0%BD%D0%B8%D0%B9-%D0%B2%D0%B8%D0%B4) | Дважды показан заголовок "Внешний вид" |
| P3 | [BUG-006](BUGS.md#bug-006---p3---%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%BE%D0%B5-%D1%84%D0%BE%D1%82%D0%BE-%D1%81%D1%82%D0%BE%D0%B8%D1%82-%D0%B2%D1%82%D0%BE%D1%80%D1%8B%D0%BC-%D0%B2-%D0%B3%D0%B0%D0%BB%D0%B5%D1%80%D0%B5%D0%B5) | Главное фото стоит вторым в галерее |

<details>
<summary>По порядку находок</summary>

- [BUG-001](BUGS.md#bug-001---p3---%D0%B4%D0%B2%D0%B0%D0%B6%D0%B4%D1%8B-%D0%BF%D0%BE%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD-%D0%B7%D0%B0%D0%B3%D0%BE%D0%BB%D0%BE%D0%B2%D0%BE%D0%BA-%D0%B2%D0%BD%D0%B5%D1%88%D0%BD%D0%B8%D0%B9-%D0%B2%D0%B8%D0%B4) - P3 - Дважды показан заголовок "Внешний вид"
- [BUG-002](BUGS.md#bug-002---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D0%BF%D1%80%D0%BE%D0%B4%D0%B0%D0%B2%D1%86%D0%B0-%D1%83%D0%B3%D0%BB%D0%BE%D0%B2%D0%BE%D0%B9-%D0%B4%D0%B8%D0%B2%D0%B0%D0%BD-%D0%BD%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD-%D0%BF%D1%80%D1%8F%D0%BC%D1%8B%D0%BC) - P2 - На карточке продавца угловой диван назван прямым
- [BUG-003](BUGS.md#bug-003---p2---%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5-%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B0%D1%85-%D0%B4%D0%BB%D0%B8%D0%BD%D0%BD%D0%B5%D0%B5-%D1%87%D0%B5%D0%BC-%D0%B2-%D1%84%D0%BE%D1%80%D0%BC%D0%B5) - P2 - Описание на карточках длиннее, чем в форме
- [BUG-004](BUGS.md#bug-004---p2---%D0%B7%D0%B0-%D0%BD%D0%B5%D0%B4%D0%B5%D0%BB%D1%8E-%D0%B5%D1%81%D1%82%D1%8C-8-%D0%BF%D1%80%D0%BE%D1%81%D0%BC%D0%BE%D1%82%D1%80%D0%BE%D0%B2-%D0%B0-%D0%B3%D1%80%D0%B0%D1%84%D0%B8%D0%BA-%D0%BF%D1%83%D1%81%D1%82) - P2 - За неделю есть 8 просмотров, а график пуст
- [BUG-005](BUGS.md#bug-005---p2---%D1%83-%D0%B0%D0%BA%D1%86%D0%B8%D0%B8-%D1%82%D0%B5%D1%85%D0%BD%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%BE%D0%B5-%D0%BD%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5-%D0%B8-%D1%81%D0%BB%D0%BE%D0%BC%D0%B0%D0%BD%D0%BD%D0%B0%D1%8F-%D0%B8%D0%BA%D0%BE%D0%BD%D0%BA%D0%B0) - P2 - У акции техническое название и сломанная иконка
- [BUG-006](BUGS.md#bug-006---p3---%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%BE%D0%B5-%D1%84%D0%BE%D1%82%D0%BE-%D1%81%D1%82%D0%BE%D0%B8%D1%82-%D0%B2%D1%82%D0%BE%D1%80%D1%8B%D0%BC-%D0%B2-%D0%B3%D0%B0%D0%BB%D0%B5%D1%80%D0%B5%D0%B5) - P3 - Главное фото стоит вторым в галерее

</details>

<details>
<summary>По группам P0-P3</summary>

**P2**

- [BUG-002](BUGS.md#bug-002---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D0%BF%D1%80%D0%BE%D0%B4%D0%B0%D0%B2%D1%86%D0%B0-%D1%83%D0%B3%D0%BB%D0%BE%D0%B2%D0%BE%D0%B9-%D0%B4%D0%B8%D0%B2%D0%B0%D0%BD-%D0%BD%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD-%D0%BF%D1%80%D1%8F%D0%BC%D1%8B%D0%BC) - На карточке продавца угловой диван назван прямым
- [BUG-003](BUGS.md#bug-003---p2---%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5-%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B0%D1%85-%D0%B4%D0%BB%D0%B8%D0%BD%D0%BD%D0%B5%D0%B5-%D1%87%D0%B5%D0%BC-%D0%B2-%D1%84%D0%BE%D1%80%D0%BC%D0%B5) - Описание на карточках длиннее, чем в форме
- [BUG-004](BUGS.md#bug-004---p2---%D0%B7%D0%B0-%D0%BD%D0%B5%D0%B4%D0%B5%D0%BB%D1%8E-%D0%B5%D1%81%D1%82%D1%8C-8-%D0%BF%D1%80%D0%BE%D1%81%D0%BC%D0%BE%D1%82%D1%80%D0%BE%D0%B2-%D0%B0-%D0%B3%D1%80%D0%B0%D1%84%D0%B8%D0%BA-%D0%BF%D1%83%D1%81%D1%82) - За неделю есть 8 просмотров, а график пуст
- [BUG-005](BUGS.md#bug-005---p2---%D1%83-%D0%B0%D0%BA%D1%86%D0%B8%D0%B8-%D1%82%D0%B5%D1%85%D0%BD%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%BE%D0%B5-%D0%BD%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5-%D0%B8-%D1%81%D0%BB%D0%BE%D0%BC%D0%B0%D0%BD%D0%BD%D0%B0%D1%8F-%D0%B8%D0%BA%D0%BE%D0%BD%D0%BA%D0%B0) - У акции техническое название и сломанная иконка

**P3**

- [BUG-001](BUGS.md#bug-001---p3---%D0%B4%D0%B2%D0%B0%D0%B6%D0%B4%D1%8B-%D0%BF%D0%BE%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD-%D0%B7%D0%B0%D0%B3%D0%BE%D0%BB%D0%BE%D0%B2%D0%BE%D0%BA-%D0%B2%D0%BD%D0%B5%D1%88%D0%BD%D0%B8%D0%B9-%D0%B2%D0%B8%D0%B4) - Дважды показан заголовок "Внешний вид"
- [BUG-006](BUGS.md#bug-006---p3---%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%BE%D0%B5-%D1%84%D0%BE%D1%82%D0%BE-%D1%81%D1%82%D0%BE%D0%B8%D1%82-%D0%B2%D1%82%D0%BE%D1%80%D1%8B%D0%BC-%D0%B2-%D0%B3%D0%B0%D0%BB%D0%B5%D1%80%D0%B5%D0%B5) - Главное фото стоит вторым в галерее

</details>
<!-- END BUG INDEX -->
