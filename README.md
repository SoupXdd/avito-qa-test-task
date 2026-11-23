![Офисные гремлины кланяются логотипу Авито](pictures/avito-office-goblins.gif)

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
| P1 | [BUG-007](BUGS.md#bug-007---p1---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D1%82%D0%BE%D0%B2%D0%B0%D1%80%D0%B0-%D1%83%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD%D0%BE-52-000--%D0%B2%D0%BC%D0%B5%D1%81%D1%82%D0%BE-25-000-) | На карточке товара указано 52 000 ₽ вместо 25 000 ₽ |
| P1 | [BUG-008](BUGS.md#bug-008---p1---%D0%B2%D0%BA%D0%BB%D1%8E%D1%87%D0%B5%D0%BD%D0%BD%D0%B0%D1%8F-%D0%B4%D0%BE%D1%81%D1%82%D0%B0%D0%B2%D0%BA%D0%B0-%D0%BF%D0%BE%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD%D0%B0-%D0%BA%D0%B0%D0%BA-%D0%BE%D1%82%D0%BA%D0%BB%D1%8E%D1%87%D0%B5%D0%BD%D0%BD%D0%B0%D1%8F-%D0%BF%D1%80%D0%BE%D0%B4%D0%B0%D0%B2%D1%86%D0%BE%D0%BC) | Включенная доставка показана как отключенная продавцом |
| P1 | [BUG-013](BUGS.md#bug-013---p1---%D0%B2-%D0%B0%D0%B4%D1%80%D0%B5%D1%81%D0%B5-%D1%83%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD-%D0%B4%D0%BE%D0%BC-5-%D0%B2%D0%BC%D0%B5%D1%81%D1%82%D0%BE-%D0%B4%D0%BE%D0%BC%D0%B0-3) | В адресе указан дом 5 вместо дома 3 |
| P2 | [BUG-002](BUGS.md#bug-002---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D0%BF%D1%80%D0%BE%D0%B4%D0%B0%D0%B2%D1%86%D0%B0-%D1%83%D0%B3%D0%BB%D0%BE%D0%B2%D0%BE%D0%B9-%D0%B4%D0%B8%D0%B2%D0%B0%D0%BD-%D0%BD%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD-%D0%BF%D1%80%D1%8F%D0%BC%D1%8B%D0%BC) | На карточке продавца угловой диван назван прямым |
| P2 | [BUG-003](BUGS.md#bug-003---p2---%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5-%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B0%D1%85-%D0%B4%D0%BB%D0%B8%D0%BD%D0%BD%D0%B5%D0%B5-%D1%87%D0%B5%D0%BC-%D0%B2-%D1%84%D0%BE%D1%80%D0%BC%D0%B5) | Описание на карточках длиннее, чем в форме |
| P2 | [BUG-004](BUGS.md#bug-004---p2---%D0%B7%D0%B0-%D0%BD%D0%B5%D0%B4%D0%B5%D0%BB%D1%8E-%D0%B5%D1%81%D1%82%D1%8C-8-%D0%BF%D1%80%D0%BE%D1%81%D0%BC%D0%BE%D1%82%D1%80%D0%BE%D0%B2-%D0%B0-%D0%B3%D1%80%D0%B0%D1%84%D0%B8%D0%BA-%D0%BF%D1%83%D1%81%D1%82) | За неделю есть 8 просмотров, а график пуст |
| P2 | [BUG-005](BUGS.md#bug-005---p2---%D0%B2-%D0%BD%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD%D0%B8%D0%B8-%D0%B0%D0%BA%D1%86%D0%B8%D0%B8-%D0%B2%D0%B8%D0%B4%D0%B5%D0%BD-%D0%B8%D0%B4%D0%B5%D0%BD%D1%82%D0%B8%D1%84%D0%B8%D0%BA%D0%B0%D1%82%D0%BE%D1%80-test_sale_ops) | В названии акции виден идентификатор test_sale_ops |
| P2 | [BUG-009](BUGS.md#bug-009---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D1%83%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD%D0%BE-3-%D0%B4%D0%B8%D0%B2%D0%B0%D0%BD%D0%B0-%D0%B2-%D0%BD%D0%B0%D0%BB%D0%B8%D1%87%D0%B8%D0%B8-%D0%B2%D0%BC%D0%B5%D1%81%D1%82%D0%BE-1) | На карточке указано 3 дивана в наличии вместо 1 |
| P2 | [BUG-010](BUGS.md#bug-010---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D1%83%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD%D0%BE-%D1%87%D1%82%D0%BE-%D0%BE%D0%B1%D0%B8%D0%B2%D0%BA%D0%B0-%D0%BC%D0%B5%D1%85%D0%B0%D0%BD%D0%B8%D0%B7%D0%BC-%D0%B8-%D0%BA%D0%B0%D1%80%D0%BA%D0%B0%D1%81-%D1%82%D1%80%D0%B5%D0%B1%D1%83%D1%8E%D1%82-%D0%B7%D0%B0%D0%BC%D0%B5%D0%BD%D1%8B-%D0%B8%D0%BB%D0%B8-%D1%80%D0%B5%D0%BC%D0%BE%D0%BD%D1%82%D0%B0) | На карточке указано, что обивка, механизм и каркас требуют замены или ремонта |
| P2 | [BUG-011](BUGS.md#bug-011---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D0%BD%D0%B5%D0%B2%D0%B5%D1%80%D0%BD%D0%BE-%D0%BF%D0%BE%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD%D1%8B-%D1%84%D0%BE%D1%80%D0%BC%D0%B0-%D0%B8-%D0%BE%D1%81%D0%BD%D0%B0%D1%89%D0%B5%D0%BD%D0%B8%D0%B5-%D0%B4%D0%B8%D0%B2%D0%B0%D0%BD%D0%B0) | На карточке неверно показаны форма и оснащение дивана |
| P2 | [BUG-012](BUGS.md#bug-012---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D0%BF%D0%B5%D1%80%D0%B5%D0%BF%D1%83%D1%82%D0%B0%D0%BD%D1%8B-%D0%B2%D1%8B%D1%81%D0%BE%D1%82%D0%B0-%D0%B8-%D0%B3%D0%BB%D1%83%D0%B1%D0%B8%D0%BD%D0%B0-%D0%B4%D0%B8%D0%B2%D0%B0%D0%BD%D0%B0) | На карточке перепутаны высота и глубина дивана |
| P3 | [BUG-001](BUGS.md#bug-001---p3---%D0%B4%D0%B2%D0%B0%D0%B6%D0%B4%D1%8B-%D0%BF%D0%BE%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD-%D0%B7%D0%B0%D0%B3%D0%BE%D0%BB%D0%BE%D0%B2%D0%BE%D0%BA-%D0%B2%D0%BD%D0%B5%D1%88%D0%BD%D0%B8%D0%B9-%D0%B2%D0%B8%D0%B4) | Дважды показан заголовок "Внешний вид" |
| P3 | [BUG-006](BUGS.md#bug-006---p3---%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%BE%D0%B5-%D1%84%D0%BE%D1%82%D0%BE-%D1%81%D1%82%D0%BE%D0%B8%D1%82-%D0%B2%D1%82%D0%BE%D1%80%D1%8B%D0%BC-%D0%B2-%D0%B3%D0%B0%D0%BB%D0%B5%D1%80%D0%B5%D0%B5) | Главное фото стоит вторым в галерее |
| P3 | [BUG-014](BUGS.md#bug-014---p3---%D0%BD%D0%B5-%D0%BE%D1%82%D0%BE%D0%B1%D1%80%D0%B0%D0%B6%D0%B0%D0%B5%D1%82%D1%81%D1%8F-%D0%B8%D0%B7%D0%BE%D0%B1%D1%80%D0%B0%D0%B6%D0%B5%D0%BD%D0%B8%D0%B5-%D0%B2-%D0%B1%D0%B0%D0%BD%D0%BD%D0%B5%D1%80%D0%B5-%D0%BF%D1%80%D0%BE-%D1%80%D0%B5%D1%81%D0%B5%D0%B9%D0%BB) | Не отображается изображение в баннере про ресейл |
| P3 | [BUG-015](BUGS.md#bug-015---p3---%D0%BD%D0%B0-%D1%88%D0%BA%D0%B0%D0%BB%D0%B5-%D0%BF%D1%80%D0%BE%D1%81%D0%BC%D0%BE%D1%82%D1%80%D0%BE%D0%B2-%D1%81%D0%B1%D0%B8%D1%82%D0%B0-%D0%BF%D0%BE%D0%B4%D0%BF%D0%B8%D1%81%D1%8C-%D0%B4%D0%B5%D0%BB%D0%B5%D0%BD%D0%B8%D1%8F-6) | На шкале просмотров сбита подпись деления 6 |
| P3 | [BUG-016](BUGS.md#bug-016---p3---%D0%BD%D0%B5-%D0%BE%D1%82%D0%BE%D0%B1%D1%80%D0%B0%D0%B6%D0%B0%D0%B5%D1%82%D1%81%D1%8F-%D0%B8%D0%BA%D0%BE%D0%BD%D0%BA%D0%B0-%D0%B0%D0%BA%D1%86%D0%B8%D0%B8) | Не отображается иконка акции |

<details>
<summary>По порядку находок</summary>

- [BUG-001](BUGS.md#bug-001---p3---%D0%B4%D0%B2%D0%B0%D0%B6%D0%B4%D1%8B-%D0%BF%D0%BE%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD-%D0%B7%D0%B0%D0%B3%D0%BE%D0%BB%D0%BE%D0%B2%D0%BE%D0%BA-%D0%B2%D0%BD%D0%B5%D1%88%D0%BD%D0%B8%D0%B9-%D0%B2%D0%B8%D0%B4) - P3 - Дважды показан заголовок "Внешний вид"
- [BUG-002](BUGS.md#bug-002---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D0%BF%D1%80%D0%BE%D0%B4%D0%B0%D0%B2%D1%86%D0%B0-%D1%83%D0%B3%D0%BB%D0%BE%D0%B2%D0%BE%D0%B9-%D0%B4%D0%B8%D0%B2%D0%B0%D0%BD-%D0%BD%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD-%D0%BF%D1%80%D1%8F%D0%BC%D1%8B%D0%BC) - P2 - На карточке продавца угловой диван назван прямым
- [BUG-003](BUGS.md#bug-003---p2---%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5-%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B0%D1%85-%D0%B4%D0%BB%D0%B8%D0%BD%D0%BD%D0%B5%D0%B5-%D1%87%D0%B5%D0%BC-%D0%B2-%D1%84%D0%BE%D1%80%D0%BC%D0%B5) - P2 - Описание на карточках длиннее, чем в форме
- [BUG-004](BUGS.md#bug-004---p2---%D0%B7%D0%B0-%D0%BD%D0%B5%D0%B4%D0%B5%D0%BB%D1%8E-%D0%B5%D1%81%D1%82%D1%8C-8-%D0%BF%D1%80%D0%BE%D1%81%D0%BC%D0%BE%D1%82%D1%80%D0%BE%D0%B2-%D0%B0-%D0%B3%D1%80%D0%B0%D1%84%D0%B8%D0%BA-%D0%BF%D1%83%D1%81%D1%82) - P2 - За неделю есть 8 просмотров, а график пуст
- [BUG-005](BUGS.md#bug-005---p2---%D0%B2-%D0%BD%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD%D0%B8%D0%B8-%D0%B0%D0%BA%D1%86%D0%B8%D0%B8-%D0%B2%D0%B8%D0%B4%D0%B5%D0%BD-%D0%B8%D0%B4%D0%B5%D0%BD%D1%82%D0%B8%D1%84%D0%B8%D0%BA%D0%B0%D1%82%D0%BE%D1%80-test_sale_ops) - P2 - В названии акции виден идентификатор test_sale_ops
- [BUG-006](BUGS.md#bug-006---p3---%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%BE%D0%B5-%D1%84%D0%BE%D1%82%D0%BE-%D1%81%D1%82%D0%BE%D0%B8%D1%82-%D0%B2%D1%82%D0%BE%D1%80%D1%8B%D0%BC-%D0%B2-%D0%B3%D0%B0%D0%BB%D0%B5%D1%80%D0%B5%D0%B5) - P3 - Главное фото стоит вторым в галерее
- [BUG-007](BUGS.md#bug-007---p1---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D1%82%D0%BE%D0%B2%D0%B0%D1%80%D0%B0-%D1%83%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD%D0%BE-52-000--%D0%B2%D0%BC%D0%B5%D1%81%D1%82%D0%BE-25-000-) - P1 - На карточке товара указано 52 000 ₽ вместо 25 000 ₽
- [BUG-008](BUGS.md#bug-008---p1---%D0%B2%D0%BA%D0%BB%D1%8E%D1%87%D0%B5%D0%BD%D0%BD%D0%B0%D1%8F-%D0%B4%D0%BE%D1%81%D1%82%D0%B0%D0%B2%D0%BA%D0%B0-%D0%BF%D0%BE%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD%D0%B0-%D0%BA%D0%B0%D0%BA-%D0%BE%D1%82%D0%BA%D0%BB%D1%8E%D1%87%D0%B5%D0%BD%D0%BD%D0%B0%D1%8F-%D0%BF%D1%80%D0%BE%D0%B4%D0%B0%D0%B2%D1%86%D0%BE%D0%BC) - P1 - Включенная доставка показана как отключенная продавцом
- [BUG-009](BUGS.md#bug-009---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D1%83%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD%D0%BE-3-%D0%B4%D0%B8%D0%B2%D0%B0%D0%BD%D0%B0-%D0%B2-%D0%BD%D0%B0%D0%BB%D0%B8%D1%87%D0%B8%D0%B8-%D0%B2%D0%BC%D0%B5%D1%81%D1%82%D0%BE-1) - P2 - На карточке указано 3 дивана в наличии вместо 1
- [BUG-010](BUGS.md#bug-010---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D1%83%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD%D0%BE-%D1%87%D1%82%D0%BE-%D0%BE%D0%B1%D0%B8%D0%B2%D0%BA%D0%B0-%D0%BC%D0%B5%D1%85%D0%B0%D0%BD%D0%B8%D0%B7%D0%BC-%D0%B8-%D0%BA%D0%B0%D1%80%D0%BA%D0%B0%D1%81-%D1%82%D1%80%D0%B5%D0%B1%D1%83%D1%8E%D1%82-%D0%B7%D0%B0%D0%BC%D0%B5%D0%BD%D1%8B-%D0%B8%D0%BB%D0%B8-%D1%80%D0%B5%D0%BC%D0%BE%D0%BD%D1%82%D0%B0) - P2 - На карточке указано, что обивка, механизм и каркас требуют замены или ремонта
- [BUG-011](BUGS.md#bug-011---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D0%BD%D0%B5%D0%B2%D0%B5%D1%80%D0%BD%D0%BE-%D0%BF%D0%BE%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD%D1%8B-%D1%84%D0%BE%D1%80%D0%BC%D0%B0-%D0%B8-%D0%BE%D1%81%D0%BD%D0%B0%D1%89%D0%B5%D0%BD%D0%B8%D0%B5-%D0%B4%D0%B8%D0%B2%D0%B0%D0%BD%D0%B0) - P2 - На карточке неверно показаны форма и оснащение дивана
- [BUG-012](BUGS.md#bug-012---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D0%BF%D0%B5%D1%80%D0%B5%D0%BF%D1%83%D1%82%D0%B0%D0%BD%D1%8B-%D0%B2%D1%8B%D1%81%D0%BE%D1%82%D0%B0-%D0%B8-%D0%B3%D0%BB%D1%83%D0%B1%D0%B8%D0%BD%D0%B0-%D0%B4%D0%B8%D0%B2%D0%B0%D0%BD%D0%B0) - P2 - На карточке перепутаны высота и глубина дивана
- [BUG-013](BUGS.md#bug-013---p1---%D0%B2-%D0%B0%D0%B4%D1%80%D0%B5%D1%81%D0%B5-%D1%83%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD-%D0%B4%D0%BE%D0%BC-5-%D0%B2%D0%BC%D0%B5%D1%81%D1%82%D0%BE-%D0%B4%D0%BE%D0%BC%D0%B0-3) - P1 - В адресе указан дом 5 вместо дома 3
- [BUG-014](BUGS.md#bug-014---p3---%D0%BD%D0%B5-%D0%BE%D1%82%D0%BE%D0%B1%D1%80%D0%B0%D0%B6%D0%B0%D0%B5%D1%82%D1%81%D1%8F-%D0%B8%D0%B7%D0%BE%D0%B1%D1%80%D0%B0%D0%B6%D0%B5%D0%BD%D0%B8%D0%B5-%D0%B2-%D0%B1%D0%B0%D0%BD%D0%BD%D0%B5%D1%80%D0%B5-%D0%BF%D1%80%D0%BE-%D1%80%D0%B5%D1%81%D0%B5%D0%B9%D0%BB) - P3 - Не отображается изображение в баннере про ресейл
- [BUG-015](BUGS.md#bug-015---p3---%D0%BD%D0%B0-%D1%88%D0%BA%D0%B0%D0%BB%D0%B5-%D0%BF%D1%80%D0%BE%D1%81%D0%BC%D0%BE%D1%82%D1%80%D0%BE%D0%B2-%D1%81%D0%B1%D0%B8%D1%82%D0%B0-%D0%BF%D0%BE%D0%B4%D0%BF%D0%B8%D1%81%D1%8C-%D0%B4%D0%B5%D0%BB%D0%B5%D0%BD%D0%B8%D1%8F-6) - P3 - На шкале просмотров сбита подпись деления 6
- [BUG-016](BUGS.md#bug-016---p3---%D0%BD%D0%B5-%D0%BE%D1%82%D0%BE%D0%B1%D1%80%D0%B0%D0%B6%D0%B0%D0%B5%D1%82%D1%81%D1%8F-%D0%B8%D0%BA%D0%BE%D0%BD%D0%BA%D0%B0-%D0%B0%D0%BA%D1%86%D0%B8%D0%B8) - P3 - Не отображается иконка акции

</details>

<details>
<summary>По экранам</summary>

**Заполненная форма редактирования**

- [BUG-001](BUGS.md#bug-001---p3---%D0%B4%D0%B2%D0%B0%D0%B6%D0%B4%D1%8B-%D0%BF%D0%BE%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD-%D0%B7%D0%B0%D0%B3%D0%BE%D0%BB%D0%BE%D0%B2%D0%BE%D0%BA-%D0%B2%D0%BD%D0%B5%D1%88%D0%BD%D0%B8%D0%B9-%D0%B2%D0%B8%D0%B4) - P3 - Дважды показан заголовок "Внешний вид"

**Карточка продавца**

- [BUG-002](BUGS.md#bug-002---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D0%BF%D1%80%D0%BE%D0%B4%D0%B0%D0%B2%D1%86%D0%B0-%D1%83%D0%B3%D0%BB%D0%BE%D0%B2%D0%BE%D0%B9-%D0%B4%D0%B8%D0%B2%D0%B0%D0%BD-%D0%BD%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD-%D0%BF%D1%80%D1%8F%D0%BC%D1%8B%D0%BC) - P2 - На карточке продавца угловой диван назван прямым
- [BUG-003](BUGS.md#bug-003---p2---%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5-%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B0%D1%85-%D0%B4%D0%BB%D0%B8%D0%BD%D0%BD%D0%B5%D0%B5-%D1%87%D0%B5%D0%BC-%D0%B2-%D1%84%D0%BE%D1%80%D0%BC%D0%B5) - P2 - Описание на карточках длиннее, чем в форме
- [BUG-004](BUGS.md#bug-004---p2---%D0%B7%D0%B0-%D0%BD%D0%B5%D0%B4%D0%B5%D0%BB%D1%8E-%D0%B5%D1%81%D1%82%D1%8C-8-%D0%BF%D1%80%D0%BE%D1%81%D0%BC%D0%BE%D1%82%D1%80%D0%BE%D0%B2-%D0%B0-%D0%B3%D1%80%D0%B0%D1%84%D0%B8%D0%BA-%D0%BF%D1%83%D1%81%D1%82) - P2 - За неделю есть 8 просмотров, а график пуст
- [BUG-005](BUGS.md#bug-005---p2---%D0%B2-%D0%BD%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD%D0%B8%D0%B8-%D0%B0%D0%BA%D1%86%D0%B8%D0%B8-%D0%B2%D0%B8%D0%B4%D0%B5%D0%BD-%D0%B8%D0%B4%D0%B5%D0%BD%D1%82%D0%B8%D1%84%D0%B8%D0%BA%D0%B0%D1%82%D0%BE%D1%80-test_sale_ops) - P2 - В названии акции виден идентификатор test_sale_ops
- [BUG-006](BUGS.md#bug-006---p3---%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%BE%D0%B5-%D1%84%D0%BE%D1%82%D0%BE-%D1%81%D1%82%D0%BE%D0%B8%D1%82-%D0%B2%D1%82%D0%BE%D1%80%D1%8B%D0%BC-%D0%B2-%D0%B3%D0%B0%D0%BB%D0%B5%D1%80%D0%B5%D0%B5) - P3 - Главное фото стоит вторым в галерее
- [BUG-015](BUGS.md#bug-015---p3---%D0%BD%D0%B0-%D1%88%D0%BA%D0%B0%D0%BB%D0%B5-%D0%BF%D1%80%D0%BE%D1%81%D0%BC%D0%BE%D1%82%D1%80%D0%BE%D0%B2-%D1%81%D0%B1%D0%B8%D1%82%D0%B0-%D0%BF%D0%BE%D0%B4%D0%BF%D0%B8%D1%81%D1%8C-%D0%B4%D0%B5%D0%BB%D0%B5%D0%BD%D0%B8%D1%8F-6) - P3 - На шкале просмотров сбита подпись деления 6
- [BUG-016](BUGS.md#bug-016---p3---%D0%BD%D0%B5-%D0%BE%D1%82%D0%BE%D0%B1%D1%80%D0%B0%D0%B6%D0%B0%D0%B5%D1%82%D1%81%D1%8F-%D0%B8%D0%BA%D0%BE%D0%BD%D0%BA%D0%B0-%D0%B0%D0%BA%D1%86%D0%B8%D0%B8) - P3 - Не отображается иконка акции

**Карточка товара**

- [BUG-003](BUGS.md#bug-003---p2---%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5-%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B0%D1%85-%D0%B4%D0%BB%D0%B8%D0%BD%D0%BD%D0%B5%D0%B5-%D1%87%D0%B5%D0%BC-%D0%B2-%D1%84%D0%BE%D1%80%D0%BC%D0%B5) - P2 - Описание на карточках длиннее, чем в форме
- [BUG-006](BUGS.md#bug-006---p3---%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%BE%D0%B5-%D1%84%D0%BE%D1%82%D0%BE-%D1%81%D1%82%D0%BE%D0%B8%D1%82-%D0%B2%D1%82%D0%BE%D1%80%D1%8B%D0%BC-%D0%B2-%D0%B3%D0%B0%D0%BB%D0%B5%D1%80%D0%B5%D0%B5) - P3 - Главное фото стоит вторым в галерее
- [BUG-007](BUGS.md#bug-007---p1---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D1%82%D0%BE%D0%B2%D0%B0%D1%80%D0%B0-%D1%83%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD%D0%BE-52-000--%D0%B2%D0%BC%D0%B5%D1%81%D1%82%D0%BE-25-000-) - P1 - На карточке товара указано 52 000 ₽ вместо 25 000 ₽
- [BUG-008](BUGS.md#bug-008---p1---%D0%B2%D0%BA%D0%BB%D1%8E%D1%87%D0%B5%D0%BD%D0%BD%D0%B0%D1%8F-%D0%B4%D0%BE%D1%81%D1%82%D0%B0%D0%B2%D0%BA%D0%B0-%D0%BF%D0%BE%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD%D0%B0-%D0%BA%D0%B0%D0%BA-%D0%BE%D1%82%D0%BA%D0%BB%D1%8E%D1%87%D0%B5%D0%BD%D0%BD%D0%B0%D1%8F-%D0%BF%D1%80%D0%BE%D0%B4%D0%B0%D0%B2%D1%86%D0%BE%D0%BC) - P1 - Включенная доставка показана как отключенная продавцом
- [BUG-009](BUGS.md#bug-009---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D1%83%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD%D0%BE-3-%D0%B4%D0%B8%D0%B2%D0%B0%D0%BD%D0%B0-%D0%B2-%D0%BD%D0%B0%D0%BB%D0%B8%D1%87%D0%B8%D0%B8-%D0%B2%D0%BC%D0%B5%D1%81%D1%82%D0%BE-1) - P2 - На карточке указано 3 дивана в наличии вместо 1
- [BUG-010](BUGS.md#bug-010---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D1%83%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD%D0%BE-%D1%87%D1%82%D0%BE-%D0%BE%D0%B1%D0%B8%D0%B2%D0%BA%D0%B0-%D0%BC%D0%B5%D1%85%D0%B0%D0%BD%D0%B8%D0%B7%D0%BC-%D0%B8-%D0%BA%D0%B0%D1%80%D0%BA%D0%B0%D1%81-%D1%82%D1%80%D0%B5%D0%B1%D1%83%D1%8E%D1%82-%D0%B7%D0%B0%D0%BC%D0%B5%D0%BD%D1%8B-%D0%B8%D0%BB%D0%B8-%D1%80%D0%B5%D0%BC%D0%BE%D0%BD%D1%82%D0%B0) - P2 - На карточке указано, что обивка, механизм и каркас требуют замены или ремонта
- [BUG-011](BUGS.md#bug-011---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D0%BD%D0%B5%D0%B2%D0%B5%D1%80%D0%BD%D0%BE-%D0%BF%D0%BE%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD%D1%8B-%D1%84%D0%BE%D1%80%D0%BC%D0%B0-%D0%B8-%D0%BE%D1%81%D0%BD%D0%B0%D1%89%D0%B5%D0%BD%D0%B8%D0%B5-%D0%B4%D0%B8%D0%B2%D0%B0%D0%BD%D0%B0) - P2 - На карточке неверно показаны форма и оснащение дивана
- [BUG-012](BUGS.md#bug-012---p2---%D0%BD%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B5-%D0%BF%D0%B5%D1%80%D0%B5%D0%BF%D1%83%D1%82%D0%B0%D0%BD%D1%8B-%D0%B2%D1%8B%D1%81%D0%BE%D1%82%D0%B0-%D0%B8-%D0%B3%D0%BB%D1%83%D0%B1%D0%B8%D0%BD%D0%B0-%D0%B4%D0%B8%D0%B2%D0%B0%D0%BD%D0%B0) - P2 - На карточке перепутаны высота и глубина дивана
- [BUG-013](BUGS.md#bug-013---p1---%D0%B2-%D0%B0%D0%B4%D1%80%D0%B5%D1%81%D0%B5-%D1%83%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD-%D0%B4%D0%BE%D0%BC-5-%D0%B2%D0%BC%D0%B5%D1%81%D1%82%D0%BE-%D0%B4%D0%BE%D0%BC%D0%B0-3) - P1 - В адресе указан дом 5 вместо дома 3
- [BUG-014](BUGS.md#bug-014---p3---%D0%BD%D0%B5-%D0%BE%D1%82%D0%BE%D0%B1%D1%80%D0%B0%D0%B6%D0%B0%D0%B5%D1%82%D1%81%D1%8F-%D0%B8%D0%B7%D0%BE%D0%B1%D1%80%D0%B0%D0%B6%D0%B5%D0%BD%D0%B8%D0%B5-%D0%B2-%D0%B1%D0%B0%D0%BD%D0%BD%D0%B5%D1%80%D0%B5-%D0%BF%D1%80%D0%BE-%D1%80%D0%B5%D1%81%D0%B5%D0%B9%D0%BB) - P3 - Не отображается изображение в баннере про ресейл

</details>
<!-- END BUG INDEX -->
