import re
import unicodedata
from pathlib import Path
from urllib.parse import quote

ROOT = Path(__file__).resolve().parent
START = "<!-- BEGIN BUG INDEX -->"
END = "<!-- END BUG INDEX -->"
BUG_HEADING = re.compile(r"(?m)^#{2,6} ((BUG-\d+) [-·] (P[0-3]) [-·] (.+))$")


def github_anchor(heading):
    slug = "".join(
        char for char in heading.lower()
        if char in " _-" or unicodedata.category(char)[0] in "LMN"
    )
    return quote(slug.replace(" ", "-"))


def render_index(report):
    bugs = []
    seen_ids = set()
    for match in BUG_HEADING.finditer(report):
        heading, bug_id, priority, title = match.groups()
        if bug_id in seen_ids:
            raise ValueError(f"Повторяющийся ID в BUGS.md: {bug_id}")
        seen_ids.add(bug_id)
        title = title.strip()
        anchor = github_anchor(heading.rstrip())
        link = f"[{bug_id}](BUGS.md#{anchor})"
        bugs.append((priority, link, title))

    if not bugs:
        return "В отчёте пока нет дефектов."

    by_priority = sorted(bugs, key=lambda bug: bug[0])
    lines = ["### По приоритету", "", "| Приоритет | ID | Дефект |", "| --- | --- | --- |"]
    for priority, link, title in by_priority:
        table_title = title.replace("|", r"\|")
        lines.append(f"| {priority} | {link} | {table_title} |")

    lines.extend(["", "<details>", "<summary>По порядку находок</summary>", ""])
    lines.extend(f"- {link} - {priority} - {title}" for priority, link, title in bugs)
    lines.extend(["", "</details>", "", "<details>", "<summary>По группам P0-P3</summary>"])
    for priority in sorted({bug[0] for bug in bugs}):
        lines.extend(["", f"**{priority}**", ""])
        lines.extend(f"- {link} - {title}" for value, link, title in by_priority if value == priority)
    lines.extend(["", "</details>"])
    return "\n".join(lines)


def main():
    readme_path = ROOT / "README.md"
    readme = readme_path.read_text(encoding="utf-8")
    if readme.count(START) != 1 or readme.count(END) != 1 or readme.index(START) > readme.index(END):
        raise SystemExit("В README.md нужна одна пара маркеров BEGIN/END BUG INDEX.")

    intro, rest = readme.split(START, 1)
    _, outro = rest.split(END, 1)
    report = (ROOT / "BUGS.md").read_text(encoding="utf-8")
    index = render_index(report)
    updated = f"{intro}{START}\n{index}\n{END}{outro}"
    if updated != readme:
        readme_path.write_text(updated, encoding="utf-8")


if __name__ == "__main__":
    main()
